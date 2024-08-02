package teamkiim.koffeechat.domain.post.community.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.post.community.controller.dto.ModifyCommunityPostRequest;
import teamkiim.koffeechat.domain.post.community.controller.dto.SaveCommunityPostRequest;
import teamkiim.koffeechat.domain.post.community.service.CommunityPostService;
import teamkiim.koffeechat.domain.vote.dto.request.SaveVoteServiceRequest;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

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

        return communityPostService.saveInitCommunityPost(memberId);
    }

    /**
     * 커뮤니티 게시글 작성 취소
     */
    @AuthenticatedMemberPrincipal
    @DeleteMapping("/cancel/{postId}")
    @CommunityPostApiDocument.CancelPostApiDoc
    public ResponseEntity<?> CancelPostApiDoc(@PathVariable("postId") Long postId) {

        return communityPostService.cancelWriteCommunityPost(postId);
    }

    /**
     * 커뮤니티 게시글 작성
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/post")
    @CommunityPostApiDocument.SavePostApiDoc
    public ResponseEntity<?> savePost(
            @Valid @RequestBody SaveCommunityPostRequest saveCommunityPostRequest, HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        //커뮤니티 게시물에 투표 기능이 포함된 경우/ 아닌 경우
        SaveVoteServiceRequest saveVoteServiceRequest = saveCommunityPostRequest.getSaveVoteRequest() != null ?
                saveCommunityPostRequest.toVoteServiceRequest() : null;

        return communityPostService.saveCommunityPost(saveCommunityPostRequest.toPostServiceRequest(), saveVoteServiceRequest, memberId);

    }

    /**
     * 커뮤니티 게시글 목록 조회
     */
    @GetMapping("/list")
    @CommunityPostApiDocument.ShowListApiDoc
    public ResponseEntity<?> showList(@RequestParam("page") int page, @RequestParam("size") int size) {

        return communityPostService.findCommunityPostList(page, size);
    }

    /**
     * 커뮤니티 게시글 상세 조회
     */
    @AuthenticatedMemberPrincipal
    @GetMapping("/{postId}")
    @CommunityPostApiDocument.ShowPostApiDoc
    public ResponseEntity<?> showPost(@PathVariable("postId") Long postId, HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return communityPostService.findPost(postId, memberId);
    }

    /**
     * 커뮤니티 게시글 수정
     */
    @AuthenticatedMemberPrincipal
    @PatchMapping("/modify")
    @CommunityPostApiDocument.ModifyPostApiDoc
    public ResponseEntity<?> modifyPost(@Valid @RequestBody ModifyCommunityPostRequest modifyCommunityPostRequest,
                                        HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return communityPostService.modifyPost(modifyCommunityPostRequest.toPostServiceRequest(), modifyCommunityPostRequest.toVoteServiceRequest(), memberId);
    }

}
