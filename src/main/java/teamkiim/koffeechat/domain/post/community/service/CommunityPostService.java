package teamkiim.koffeechat.domain.post.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamkiim.koffeechat.domain.bookmark.service.BookmarkService;
import teamkiim.koffeechat.domain.file.service.FileService;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.domain.post.community.domain.CommunityPost;
import teamkiim.koffeechat.domain.post.community.dto.request.ModifyCommunityPostServiceRequest;
import teamkiim.koffeechat.domain.post.community.dto.request.SaveCommunityPostServiceRequest;
import teamkiim.koffeechat.domain.post.community.dto.response.CommentInfoDto;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostListResponse;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostResponse;
import teamkiim.koffeechat.domain.post.community.dto.response.VoteResponse;
import teamkiim.koffeechat.domain.post.community.repository.CommunityPostRepository;
import teamkiim.koffeechat.domain.postlike.repository.PostLikeRepository;
import teamkiim.koffeechat.domain.postlike.service.PostLikeService;
import teamkiim.koffeechat.domain.vote.domain.Vote;
import teamkiim.koffeechat.domain.vote.dto.request.ModifyVoteServiceRequest;
import teamkiim.koffeechat.domain.vote.dto.request.SaveVoteServiceRequest;
import teamkiim.koffeechat.domain.vote.repository.VoteRepository;
import teamkiim.koffeechat.domain.vote.service.VoteService;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final PostLikeRepository postLikeRepository;
    private final PostLikeService postLikeService;
    private final BookmarkService bookmarkService;
    private final VoteRepository voteRepository;
    private final VoteService voteService;

    /**
     * 게시글 최초 임시 저장
     *
     * @param memberId 작성자 PK
     * @return Long 게시글 PK
     */
    @Transactional
    public ResponseEntity<?> saveInitCommunityPost(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CommunityPost communityPost = CommunityPost.builder()
                .member(member)
                .isEditing(true)
                .build();

        CommunityPost saveCommunityPost = communityPostRepository.save(communityPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveCommunityPost.getId());
    }

    /**
     * 커뮤니티 게시글 작성 취소
     *
     * @param postId 게시글 PK
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> cancelWriteCommunityPost(Long postId) {

        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        fileService.deleteImageFiles(communityPost);

        communityPostRepository.delete(communityPost);

        return ResponseEntity.ok("게시글 작성 취소 완료");
    }

    /**
     * 게시글 저장
     *
     * @param saveCommunityPostServiceRequest 게시글 저장 dto
     * @return CommunityPostResponse HttpStatus created
     */
    @Transactional
    public ResponseEntity<?> saveCommunityPost(SaveCommunityPostServiceRequest saveCommunityPostServiceRequest,
                                               SaveVoteServiceRequest saveVoteServiceRequest, Long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CommunityPost communityPost = communityPostRepository.findById(saveCommunityPostServiceRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        communityPost.completeCommunityPost(saveCommunityPostServiceRequest.getTitle(), saveCommunityPostServiceRequest.getBodyContent());

        fileService.deleteImageFiles(saveCommunityPostServiceRequest.getFileIdList(), communityPost);

        List<CommentInfoDto> commentInfoDtoList = new ArrayList<>();

        if (saveVoteServiceRequest == null) {  //투표 x
            return ResponseEntity.status(HttpStatus.CREATED).body(CommunityPostResponse.of(communityPost, commentInfoDtoList, null, memberId, false, false));

        }

        //투표 o
        Vote savedVote = voteService.saveVote(saveVoteServiceRequest, saveCommunityPostServiceRequest.getId());  //투표 저장

        return ResponseEntity.status(HttpStatus.CREATED).body(CommunityPostResponse.of(communityPost, commentInfoDtoList, VoteResponse.of(savedVote, true), memberId, false, false));

    }

    /**
     * 게시글 목록 조회
     *
     * @param page 페이지 번호 ( ex) 0, 1,,,, )
     * @param size 페이지 당 조회할 데이터 수
     * @return List<CommunityPostListResponse>
     */
    public ResponseEntity<?> findCommunityPostList(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        List<CommunityPost> communityPostList = communityPostRepository.findAllCompletePost(pageRequest).getContent();

        List<CommunityPostListResponse> communityPostResponseList = communityPostList.stream()
                .map(CommunityPostListResponse::of).collect(Collectors.toList());

        return ResponseEntity.ok(communityPostResponseList);
    }

    /**
     * 게시글 상세 조회
     *
     * @param postId postId 게시글 PK
     * @return CommunityPostResponse
     */
    public ResponseEntity<?> findPost(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<CommentInfoDto> commentInfoDtoList = communityPost.getCommentList().stream()
                .map(comment -> CommentInfoDto.of(comment, memberId)).collect(Collectors.toList());

        boolean isMemberLiked = postLikeService.isMemberLiked(communityPost, member);
        Optional<Vote> vote = voteRepository.findByPost(communityPost);
        VoteResponse voteResponse;
        if (vote.isPresent()) {
            boolean isMemberVoted = voteService.hasMemberVoted(vote.get(), member);
            voteResponse = VoteResponse.of(vote.get(), isMemberVoted);
        } else voteResponse = null;

        boolean isMemberBookmarked = bookmarkService.isMemberBookmarked(member, communityPost);

        return ResponseEntity.ok(CommunityPostResponse.of(communityPost, commentInfoDtoList, voteResponse, memberId, isMemberLiked, isMemberBookmarked));
    }

    /**
     * 게시글 수정
     *
     * @param modifyCommunityPostServiceRequest 게시글 수정 dto
     * @return CommunityPostResponse
     */
    @Transactional
    public ResponseEntity<?> modifyPost(ModifyCommunityPostServiceRequest modifyCommunityPostServiceRequest,
                                        ModifyVoteServiceRequest modifyVoteServiceRequest, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CommunityPost communityPost = communityPostRepository.findById(modifyCommunityPostServiceRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        communityPost.modifyCommunityPost(modifyCommunityPostServiceRequest.getTitle(), modifyCommunityPostServiceRequest.getBodyContent());

        List<CommentInfoDto> commentInfoDtoList = communityPost.getCommentList().stream()
                .map(comment -> CommentInfoDto.of(comment, memberId)).collect(Collectors.toList());

        boolean isMemberLiked = postLikeService.isMemberLiked(communityPost, member);
        Optional<Vote> vote = voteRepository.findByPost(communityPost);  //게시글로 투표 찾아옴.
        VoteResponse voteResponse;
        //투표 유무 확인
        if (modifyVoteServiceRequest == null) {      //투표가 없는 경우
            if (vote.isPresent()) {                  //원래 투표가 있었으면 -> 투표 삭제
                voteRepository.delete(vote.get());   //투표 삭제
                voteRepository.flush();
            }
            voteResponse = null;
        } else {                                     //투표가 있는 경우
            if (vote.isPresent()) {                  // 투표 내용 수정
                voteService.modifyVote(modifyVoteServiceRequest, vote.get());
            } else {                                 //원래 투표가 없었으면 -> 새로 생성
                voteService.saveVote(modifyVoteServiceRequest.toSaveVoteServiceRequest(), communityPost.getId());
            }
            voteResponse = VoteResponse.of(vote.get(), true);
        }

        boolean isMemberBookmarked = bookmarkService.isMemberBookmarked(member, communityPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommunityPostResponse.of(communityPost, commentInfoDtoList, voteResponse, memberId, isMemberLiked, isMemberBookmarked));
    }
}
