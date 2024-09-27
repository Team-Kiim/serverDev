package teamkiim.koffeechat.domain.post.community.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.post.common.domain.SortCategory;
import teamkiim.koffeechat.domain.post.community.controller.dto.ModifyCommunityPostRequest;
import teamkiim.koffeechat.domain.post.community.controller.dto.SaveCommunityPostRequest;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostListResponse;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostResponse;
import teamkiim.koffeechat.domain.post.community.service.CommunityPostService;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-post")
@Tag(name = "커뮤니티 게시판 API")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    /**
     * 커뮤니티 게시글 최초 임시 저장
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/init")
    @CommunityPostApiDocument.InitPostApiDoc
    public ResponseEntity<?> initPost(HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        Long postId = communityPostService.saveInitCommunityPost(memberId);

        return ResponseEntity.ok(postId);
    }

    /**
     * 커뮤니티 게시글 작성 취소
     */
    @AuthenticatedMemberPrincipal
    @DeleteMapping("/{postId}")
    @CommunityPostApiDocument.CancelPostApiDoc
    public ResponseEntity<?> CancelPostApiDoc(@PathVariable("postId") Long postId) {

        communityPostService.cancelWriteCommunityPost(postId);

        return ResponseEntity.ok("게시글 작성 취소 완료");
    }

    /**
     * 커뮤니티 게시글 작성
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/{postId}")
    @CommunityPostApiDocument.SavePostApiDoc
    public ResponseEntity<?> savePost(@PathVariable("postId") Long postId, @Valid @RequestBody SaveCommunityPostRequest saveCommunityPostRequest,
                                      HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        communityPostService.saveCommunityPost(postId, saveCommunityPostRequest, memberId);

        return ResponseEntity.ok("커뮤니티 게시글 저장 완료");
    }

    /**
     * 커뮤니티 게시글 목록 조회 (필터: 제목, 태그)
     */
    @GetMapping("")
    @CommunityPostApiDocument.GetCommunityPostListApiDoc
    public ResponseEntity<?> getCommunityPostList(@RequestParam("sortType") SortCategory sortType, @RequestParam("page") int page, @RequestParam("size") int size,
                                                  @RequestParam(value = "word", required = false) String keyword, @RequestParam(value = "tag", required = false) List<String> tagContents) {

        List<CommunityPostListResponse> responses = communityPostService.findCommunityPostList(sortType, page, size, keyword, tagContents);

        return ResponseEntity.ok(responses);
    }


    /**
     * 커뮤니티 게시글 상세 조회
     */
    @AuthenticatedMemberPrincipal
    @GetMapping("/{postId}")
    @CommunityPostApiDocument.ShowPostApiDoc
    public ResponseEntity<?> showPost(@PathVariable("postId") Long postId, HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        CommunityPostResponse postResponse = communityPostService.findPost(postId, memberId, request);

        return ResponseEntity.ok(postResponse);
    }

    /**
     * 커뮤니티 게시글 수정
     */
    @AuthenticatedMemberPrincipal
    @PatchMapping("/{postId}")
    @CommunityPostApiDocument.ModifyPostApiDoc
    public ResponseEntity<?> modifyPost(@PathVariable("postId") Long postId, @Valid @RequestBody ModifyCommunityPostRequest modifyCommunityPostRequest,
                                        HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        communityPostService.modifyPost(modifyCommunityPostRequest.toPostServiceRequest(postId), modifyCommunityPostRequest.toVoteServiceRequest(), memberId);

        return ResponseEntity.ok("게시물 수정 완료");
    }

}
