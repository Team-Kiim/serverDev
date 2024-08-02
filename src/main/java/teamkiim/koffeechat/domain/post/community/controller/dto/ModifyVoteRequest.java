package teamkiim.koffeechat.domain.post.community.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "커뮤니티 게시글 투표 수정 Request")
public class ModifyVoteRequest {

    @Schema(description = "투표 제목", example = "커뮤니티 게시글 투표 제목 수정입니다.")
    @NotBlank(message = "투표 제목을 입력해 주세요.")
    private String title;

    @Schema(description = "투표 항목 리스트. 추가만 가능합니다.", example = "[\"항목 1\", \"항목 2\"]")
    @NotEmpty(message="항목을 입력해 주세요.")
    private List<String> items;
}
