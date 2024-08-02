package teamkiim.koffeechat.domain.post.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamkiim.koffeechat.domain.bookmark.domain.Bookmark;
import teamkiim.koffeechat.domain.post.common.dto.response.BookmarkPostListResponse;
import teamkiim.koffeechat.domain.bookmark.repository.BookmarkRepository;
import teamkiim.koffeechat.domain.bookmark.service.BookmarkService;
import teamkiim.koffeechat.domain.post.common.domain.Post;
import teamkiim.koffeechat.domain.post.common.repository.PostRepository;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.domain.postlike.service.PostLikeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeService postLikeService;
    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 게시물 좋아요
     * @param postId 게시물 PK
     * @param memberId 회원 PK
     * @return Long -> 게시물 좋아요 수
     */
    @Transactional
    public ResponseEntity<?> like(Long postId, Long memberId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (postLikeService.isMemberLiked(post, member)) {        // 이미 좋아요 눌렀으면
            postLikeService.cancelLike(post, member);
            post.removeLike();
        } else {                                                   // 좋아요 누르지 않았으면
            postLikeService.like(post, member);
            post.addLike();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(post.getLikeCount());
    }

    /**
     * 게시물 북마크
     * @param memberId 회원 PK
     * @param postId 게시물 PK
     * @return Long -> 게시물 북마크 수
     */
    @Transactional
    public ResponseEntity<?> bookmark(Long memberId, Long postId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (bookmarkService.isMemberBookmarked(member, post)) {     // 이미 북마크 했으면
            bookmarkService.cancelBookmark(member, post);
            post.removeBookmark();
        }else{                                                      // 북마크 누르지 않았다면
            bookmarkService.bookmark(member, post);                 // 북마크 생성
            post.addBookmark();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(post.getBookmarkCount());
    }

    /**
     * 로그인한 회원이 북마크한 게시글 목록 조회
     * @param page 페이지 번호 ( ex) 0, 1,,,, )
     * @param size 페이지 당 조회할 데이터 수
     * @return List<BookmarkPostListResponse>
     */
    public ResponseEntity<?> findBookmarkPostList(Long memberId, int page, int size) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        List<Bookmark> bookmarkList = bookmarkRepository.findAllByMember(member, pageRequest).getContent();

        List<Post> bookmarkPostList = bookmarkList.stream()
                .map(Bookmark::getPost)
                .toList();

        List<BookmarkPostListResponse> bookmarkPostResponseList = bookmarkPostList.stream()
                .map(BookmarkPostListResponse::of).toList();

        return ResponseEntity.ok(bookmarkPostResponseList);
    }

    /**
     * 게시글 삭제 (soft delete)
     * @param postId 삭제할 게시글 PK
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> softDelete(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.delete();

        return ResponseEntity.status(HttpStatus.CREATED).body(post.getId());
    }

}
