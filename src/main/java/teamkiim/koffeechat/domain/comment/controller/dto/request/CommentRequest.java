package teamkiim.koffeechat.domain.comment.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.comment.dto.request.CommentServiceRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 저장 Request")
public class CommentRequest {

    private String postId;

    @Schema(description = "댓글 내용", example = "댓글 내용입니다.")
    @NotBlank(message = "댓글을 작성해주세요.")
    private String content;

    public CommentServiceRequest toServiceRequest(Long postId, LocalDateTime currDateTime) {
        return CommentServiceRequest.builder()
                .postId(postId)
                .content(this.content)
                .currDateTime(currDateTime)
                .build();
    }
}