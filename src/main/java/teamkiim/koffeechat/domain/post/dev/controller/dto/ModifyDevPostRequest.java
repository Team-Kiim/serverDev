package teamkiim.koffeechat.domain.post.dev.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.post.dev.domain.ChildSkillCategory;
import teamkiim.koffeechat.domain.post.dev.domain.ParentSkillCategory;
import teamkiim.koffeechat.domain.post.dev.dto.request.ModifyDevPostServiceRequest;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개발 게시글 수정 Request")
public class ModifyDevPostRequest {

    @Schema(description = "수정할 개발 게시글 pk")
    private Long id;

    @Schema(description = "개발 게시글 제목", example="개발 게시글 제목 수정입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "개발 게시글 내용", example="개발 게시글 내용 수정입니다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;

    @Schema(description = "개발 게시글 연관 카테고리")
    private List<ParentSkillCategory> parentSkillCategoryList;
    private List<ChildSkillCategory> childSkillCategoryList;

    public ModifyDevPostServiceRequest toServiceRequest(){
        return ModifyDevPostServiceRequest.builder()
                .id(this.id)
                .title(this.title)
                .bodyContent(this.bodyContent)
                .parentSkillCategoryList(this.parentSkillCategoryList)
                .childSkillCategoryList(this.childSkillCategoryList)
                .build();
    }
}
