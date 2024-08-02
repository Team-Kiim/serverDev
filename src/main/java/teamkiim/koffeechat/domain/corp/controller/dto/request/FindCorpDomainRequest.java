package teamkiim.koffeechat.domain.corp.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회사 이름 검색 Request")
public class FindCorpDomainRequest {

    @Schema(description = "회사 도메인", example = "koffeechat.com")
    @Pattern(regexp = "[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "도메인 형식에 맞게 검색해주세요. \n ex) koffeechat.com, koffeechat.co.kr")
    private String corpEmailDomain;
}
