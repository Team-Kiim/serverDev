package teamkiim.koffeechat.domain.post.dev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamkiim.koffeechat.domain.bookmark.service.BookmarkService;
import teamkiim.koffeechat.domain.file.service.FileService;
import teamkiim.koffeechat.domain.post.dev.dto.response.DevPostResponse;
import teamkiim.koffeechat.domain.postlike.service.PostLikeService;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.domain.post.dev.domain.ChildSkillCategory;
import teamkiim.koffeechat.domain.post.dev.domain.DevPost;
import teamkiim.koffeechat.domain.post.dev.dto.request.ModifyDevPostServiceRequest;
import teamkiim.koffeechat.domain.post.dev.dto.request.SaveDevPostServiceRequest;
import teamkiim.koffeechat.domain.post.dev.dto.response.DevPostListResponse;
import teamkiim.koffeechat.domain.post.dev.repository.DevPostRepository;
import teamkiim.koffeechat.domain.postlike.repository.PostLikeRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 개발 게시글 관련 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DevPostService {

    private final DevPostRepository devPostRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final PostLikeRepository postLikeRepository;
    private final PostLikeService postLikeService;
    private final BookmarkService bookmarkService;

    /**
     * 게시글 최초 임시 저장
     * @param memberId 작성자 PK
     * @return Long 게시글 PK
     */
    @Transactional
    public ResponseEntity<?> saveInitDevPost(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        DevPost devPost = DevPost.builder()
                .member(member)
                .isEditing(true)
                .build();

        DevPost saveDevPost = devPostRepository.save(devPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveDevPost.getId());
    }

    /**
     * 개발 게시글 작성 취소
     * @param postId 게시글 PK
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> cancelWriteDevPost(Long postId){

        DevPost devPost = devPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        fileService.deleteImageFiles(devPost);

        devPostRepository.delete(devPost);

        return ResponseEntity.ok("게시글 삭제 완료");
    }

    /**
     * 게시글 저장
     * @param saveDevPostServiceRequest 게시글 저장 dto
     * @return DevPostResponse
     */
    @Transactional
    public ResponseEntity<?> saveDevPost(SaveDevPostServiceRequest saveDevPostServiceRequest, Long memberId){

        DevPost devPost = devPostRepository.findById(saveDevPostServiceRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        devPost.completeDevPost(saveDevPostServiceRequest.getTitle(), saveDevPostServiceRequest.getBodyContent(),
                saveDevPostServiceRequest.getSkillCategoryList());

        fileService.deleteImageFiles(saveDevPostServiceRequest.getFileIdList(), devPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(DevPostResponse.of(devPost, memberId, false, false));
    }

    /**
     * 게시글 목록 조회
     * @param page 페이지 번호 ( ex) 0, 1,,,, )
     * @param size 페이지 당 조회할 데이터 수
     * @return List<DevPostListResponse>
     */
    public ResponseEntity<?> findDevPostList(int page, int size, List<ChildSkillCategory> childSkillCategoryList){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        List<DevPost> devPostList;

        if(childSkillCategoryList == null){
            devPostList = devPostRepository.findAllCompletePostBySkillCategory(pageRequest).getContent();
        }
        else{
            devPostList = devPostRepository.findAllCompletePostBySkillCategory(childSkillCategoryList, pageRequest).getContent();
        }

        List<DevPostListResponse> devPostListResponseList = devPostList.stream()
                .map(DevPostListResponse::of).collect(Collectors.toList());

        return ResponseEntity.ok(devPostListResponseList);
    }

    /**
     * 게시글 상세 조회
     * @param postId 게시글 PK
     * @return DevPostResponse
     */
    @Transactional
    public ResponseEntity<?> findPost(Long postId, Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        DevPost devPost = devPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        boolean isMemberLiked = postLikeService.isMemberLiked(devPost, member);

        boolean isMemberBookmarked = bookmarkService.isMemberBookmarked(member, devPost);

        return ResponseEntity.ok(DevPostResponse.of(devPost, memberId, isMemberLiked, isMemberBookmarked));
    }

    /**
     * 게시글 수정
     * @param modifyDevPostServiceRequest 게시글 수정 dto
     * @return DevPostResponse
     */
    @Transactional
    public ResponseEntity<?> modifyPost(ModifyDevPostServiceRequest modifyDevPostServiceRequest, Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        DevPost devPost = devPostRepository.findById(modifyDevPostServiceRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        devPost.modify(modifyDevPostServiceRequest.getTitle(), modifyDevPostServiceRequest.getBodyContent(),
                modifyDevPostServiceRequest.combineSkillCategory());

        boolean isMemberLiked = postLikeService.isMemberLiked(devPost, member);

        boolean isMemberBookmarked = bookmarkService.isMemberBookmarked(member, devPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(DevPostResponse.of(devPost, memberId, isMemberLiked,isMemberBookmarked ));
    }




}
