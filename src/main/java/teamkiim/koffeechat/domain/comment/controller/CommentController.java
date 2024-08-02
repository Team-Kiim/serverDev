package teamkiim.koffeechat.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.comment.controller.dto.CommentRequest;
import teamkiim.koffeechat.domain.comment.controller.dto.ModifyCommentRequest;
import teamkiim.koffeechat.domain.comment.service.CommentService;
import teamkiim.koffeechat.global.Auth;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/{postId}/comments")
    @CommentApiDocument.SaveCommentApiDoc
    public ResponseEntity<?> saveComment(@PathVariable("postId") Long postId,
                                         @Valid @RequestBody CommentRequest commentRequest, HttpServletRequest request){

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        LocalDateTime currDateTime = LocalDateTime.now();

        return commentService.saveComment(postId, commentRequest.toServiceRequest(currDateTime), memberId);
    }

    /**
     * 댓글 수정
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/modify")
    @CommentApiDocument.ModifyCommentApiDoc
    public ResponseEntity<?> modifyComment(@Valid @RequestBody ModifyCommentRequest modifyCommentRequest){

        LocalDateTime currDateTime = LocalDateTime.now();

        return commentService.modifyComment(modifyCommentRequest.toServiceRequest(currDateTime));
    }

    /**
     * 댓글 삭제
     */
    @AuthenticatedMemberPrincipal
    @DeleteMapping("/delete/{commentId}")
    @CommentApiDocument.DeleteCommentApiDoc
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId){

        return commentService.deleteComment(commentId);
    }
}
