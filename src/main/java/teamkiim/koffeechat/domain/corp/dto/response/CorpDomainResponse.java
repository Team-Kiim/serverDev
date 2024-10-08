package teamkiim.koffeechat.domain.corp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.corp.domain.Corp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회사 메일 검색 Response")
@Builder
public class CorpDomainResponse {

    @Schema(description = "회사 이름", example = "커피챗")
    private String corpName;               //회사 이름

    @Schema(description = "회사 도메인", example = "koffeechat.com")
    private String corpEmailDomain;        //회사 이메일 도메인

    public static CorpDomainResponse of(Corp corp) {
        return CorpDomainResponse.builder()
                .corpName(corp.getName())
                .corpEmailDomain(corp.getEmailDomain())
                .build();
    }
}
