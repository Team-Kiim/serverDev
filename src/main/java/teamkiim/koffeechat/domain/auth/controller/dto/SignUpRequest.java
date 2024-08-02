package teamkiim.koffeechat.domain.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.auth.dto.request.SignUpServiceRequest;
import teamkiim.koffeechat.domain.member.domain.MemberRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Member 회원가입 request")
public class SignUpRequest {

    @Schema(description = "회원가입용 이메일", example = "koffeechat@naver.com")
    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "회원가입용 비밀번호", example = "1234asdf!@")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Schema(description = "회원가입용 닉네임", example = "커피챗")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_.-]{3,10}$", message = "닉네임은 3~10자의 알파벳 대소문자, 숫자, _ . -만 사용 가능합니다.")
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @Schema(description = "회원가입용 직업", defaultValue = "COMPANY_EMPLOYEE")
    private MemberRole memberRole;

    public SignUpServiceRequest toServiceRequest(){
        return SignUpServiceRequest.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .memberRole(this.memberRole)
                .build();

    }
}
