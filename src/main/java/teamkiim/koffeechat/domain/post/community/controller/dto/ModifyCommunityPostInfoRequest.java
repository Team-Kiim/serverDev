package teamkiim.koffeechat.domain.post.community.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커뮤니티 게시글 수정 Request")
public class ModifyCommunityPostInfoRequest {

    @Schema(description = "커뮤니티 게시글 pk", example = "1")
    private Long id;

    @Schema(description = "커뮤니티 게시글 제목", example = "커뮤니티 게시글 제목입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "커뮤니티 게시글 내용", example = "커뮤니티 게시글 내용입니다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;

}
