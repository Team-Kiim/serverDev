package teamkiim.koffeechat.domain.corp.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "현직자 이메일 인증 메시지 전송 Request")
public class CorpAuthRequest {

    @Schema(description = "회사 이름", example = "커피챗")
    @NotBlank(message = "회사 이름을 입력해주세요.")
    private String corpName;

    @Schema(description = "사용자 이메일", example = "koffeechat@naver.com")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
}
