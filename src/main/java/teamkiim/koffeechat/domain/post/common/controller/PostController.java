package teamkiim.koffeechat.domain.post.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.post.common.service.PostService;
import teamkiim.koffeechat.global.Auth;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "게시판 공통API")
public class PostController {

    private final PostService postService;

    /**
     * 좋아요
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/like/{postId}")
    @PostApiDocument.LikeApiDoc
    public ResponseEntity<?> like(@PathVariable("postId") Long postId, HttpServletRequest request){

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return postService.like(postId, memberId);
    }

    /**
     * 북마크
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/bookmark/{postId}")
    @PostApiDocument.BookmarkApiDoc
    public ResponseEntity<?> bookmark(@PathVariable("postId") Long postId, HttpServletRequest request){

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return postService.bookmark(memberId, postId);
    }

    /**
     * 북마크 리스트 확인
     */
    @AuthenticatedMemberPrincipal
    @Auth(role = {Auth.MemberRole.COMPANY_EMPLOYEE, Auth.MemberRole.FREELANCER, Auth.MemberRole.STUDENT,
            Auth.MemberRole.COMPANY_EMPLOYEE_TEMP, Auth.MemberRole.MANAGER, Auth.MemberRole.ADMIN})
    @GetMapping("/bookmark")
    @PostApiDocument.BookmarkedPostListApiDoc
    public ResponseEntity<?> findBookmarkedPostList(@RequestParam("page") int page, @RequestParam("size") int size,
                                                    HttpServletRequest request){

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return postService.findBookmarkPostList(memberId, page, size);
    }

    /**
     * 게시글 삭제 (soft delete)
     */
    @AuthenticatedMemberPrincipal
    @DeleteMapping("delete/{postId}")
    @PostApiDocument.DeletePostApiDoc
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId){

        return postService.softDelete(postId);
    }
}
