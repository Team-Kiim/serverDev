package teamkiim.koffeechat.domain.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.auth.dto.request.LoginServiceRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Member 로그인 Request")
public class LoginRequest {

    @Schema(description = "로그인 이메일", example = "koffeechat@naver.com")
    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "로그인 비밀번호", example = "1234asdf!@")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public LoginServiceRequest toServiceRequest(){
        return LoginServiceRequest.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
