package teamkiim.koffeechat.domain.post.community.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "커뮤니티 게시글 저장 Request")
public class SaveCommunityPostInfoRequest {

    @Schema(description = "커뮤니티 게시글 제목", example = "커뮤니티 게시글 제목입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "커뮤니티 게시글 내용", example = "커뮤니티 게시글 내용입니다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;

    private List<Long> fileIdList;

    @Schema(description = "커뮤니티 게시글 태그 리스트 (띄어쓰기x, 콤마(,) x)", example = "[\"태그_1\", \"태그_2\"]")
    private List<String> tagContentList;

}

