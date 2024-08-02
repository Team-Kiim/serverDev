package teamkiim.koffeechat.domain.corp.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "현직자 이메일 인증 메시지 확인 Request")
public class CorpAuthCodeCheckRequest {

    @Schema(description = "회사 이름", example = "커피챗")
    @NotBlank(message = "회사 이름을 입력해주세요.")
    private String corpName;

    @Schema(description = "회사 이메일", example = "koffeechat@naver.com")
    @NotBlank(message = "회사 이메일을 입력해주세요")
    private String email;

    @Schema(description = "인증 코드", example = "808080")
    @NotBlank(message = "인증 코드를 입력해주세요")
    private String code;

}
