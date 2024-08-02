package teamkiim.koffeechat.domain.corp.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회사 도메인 검색 Request")
public class FindCorpNameRequest {

    @Schema(description = "회사 이름", example = "커피챗")
    private String corpName;

}
