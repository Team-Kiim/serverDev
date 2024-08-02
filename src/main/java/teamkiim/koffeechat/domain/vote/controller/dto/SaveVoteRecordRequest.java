package teamkiim.koffeechat.domain.vote.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "커뮤니티 게시글 투표 Request")
public class SaveVoteRecordRequest {

    @Schema(description = "사용자가 투표한 투표 항목 pk list", example ="[1, 2]")
    private List<Long> items;

}
