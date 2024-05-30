package teamkiim.koffeechat.domain.post.dev.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.post.dev.domain.SkillCategory;
import teamkiim.koffeechat.domain.post.dev.dto.request.ModifyDevPostServiceRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개발 게시글 수정 Request")
public class ModifyDevPostRequest {

    @Schema(description = "개발 게시글 제목", example = "개발 게시글 제목 수정입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "개발 게시글 내용", example = "개발 게시글 내용 수정입니다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;

    @Schema(description = "시각 자료", example = "")      // 필수 아님
    private String visualData;

    @Schema(description = "개발 게시글 연관 기술 카테고리")
    @NotEmpty(message = "기술 카테고리는 최소 하나 이상 선택해야 합니다.")
    private List<SkillCategory> skillCategoryList;

    @Schema(description = "게시글 관련 파일 리스트")
    private List<String> fileUrlList;

    @Schema(description = "개발 게시글 태그 리스트 (띄어쓰기x, 콤마(,) x)", example = "[\"태그_1\", \"태그_2\"]")
    private List<String> tagContentList;

    public ModifyDevPostServiceRequest toServiceRequest() {
        return ModifyDevPostServiceRequest.builder()
                .title(this.title)
                .bodyContent(this.bodyContent)
                .visualData(this.visualData)
                .skillCategoryList(this.skillCategoryList)
                .fileUrlList(this.fileUrlList)
                .tagContentList(this.tagContentList)
                .build();
    }
}