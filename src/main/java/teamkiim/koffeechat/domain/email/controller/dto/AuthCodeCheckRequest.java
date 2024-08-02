package teamkiim.koffeechat.domain.email.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.email.dto.request.AuthCodeCheckServiceRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이메일 인증 메시지 확인 Request")
public class AuthCodeCheckRequest {

    @Schema(description = "사용자 이메일", example = "koffeechat@naver.com")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @Schema(description = "인증 코드", example = "808080")
    @NotBlank(message = "인증 코드를 입력해주세요")
    private String code;

    public AuthCodeCheckServiceRequest toServiceRequest(){
        return AuthCodeCheckServiceRequest.builder()
                .email(this.email)
                .code(this.code)
                .build();
    }
}
