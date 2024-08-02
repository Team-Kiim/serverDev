package teamkiim.koffeechat.domain.post.dev.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.post.dev.domain.SkillCategory;
import teamkiim.koffeechat.domain.post.dev.dto.request.SaveDevPostServiceRequest;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개발 게시글 저장 Request")
public class SaveDevPostRequest {

    @Schema(description = "최초 저장한 게시글 pk", example = "1")
    private Long id;

    @Schema(description = "개발 게시글 제목" , example = "개발 게시글 제목입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "개발 게시글 내용" , example = "개발 게시글 내용입니다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;

    @Schema(description = "게시글 관련 기술 카테고리")
    private List<SkillCategory> skillCategoryList;
    private List<Long> fileIdList;

    public SaveDevPostServiceRequest toServiceRequest(){
        return SaveDevPostServiceRequest.builder()
                .id(this.id)
                .title(this.title)
                .bodyContent(this.bodyContent)
                .skillCategoryList(this.skillCategoryList)
                .fileIdList(this.fileIdList)
                .build();
    }
}
