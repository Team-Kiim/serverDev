package teamkiim.koffeechat.domain.corp.controller.admindto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.corp.domain.Verified;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회사 도메인 승인 상태 변경 Request")
public class ManageCorpDomainRequest {

    @Schema(description = "회사 이름", example = "커피챗")
    @NotBlank(message = "회사 이름을 입력해주세요")
    private String corpName;               //회사 이름

    @Schema(description = "회사 도메인", example = "koffeechat.com")
    @Pattern(regexp = "[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "도메인 형식에 맞게 검색해주세요. \n ex) koffeechat.com, koffeechat.co.kr")
    @NotBlank(message = "회사 도메인을 입력해주세요")
    private String corpEmailDomain;        //회사 이메일 도메인

    @Schema(description = "회사 도메인 승인 상태", example = "APPROVED")
    @NotNull(message = "승인 여부를 선택해주세요")
    private Verified verified;
}
