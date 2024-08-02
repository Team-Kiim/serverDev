package teamkiim.koffeechat.domain.comment.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.comment.dto.request.CommentServiceRequest;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 저장 Request")
public class CommentRequest {

    @Schema(description = "댓글 내용", example = "댓글 내용입니다.")
    @NotBlank(message = "댓글을 작성해주세요.")
    String content;

    public CommentServiceRequest toServiceRequest(LocalDateTime currDateTime){
        return CommentServiceRequest.builder()
                .content(this.content)
                .currDateTime(currDateTime)
                .build();
    }
}