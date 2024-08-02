package teamkiim.koffeechat.domain.corp.controller.admindto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.corp.domain.Corp;
import teamkiim.koffeechat.domain.corp.domain.Verified;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회사 도메인 리스트 Response")
public class AdminCorpDomainListResponse {

    @Schema(description = "회사 도메인 pk",example = "1")
    private Long id;                       //도메인 pk

    @Schema(description = "회사 이름", example = "커피챗")
    private String corpName;               //회사 이름

    @Schema(description = "회사 도메인", example = "koffeechat.com")
    private String corpEmailDomain;        //회사 이메일 도메인

    @Schema(description = "도메인 승인 상태", example = "WAITING")
    private Verified verified;             //상태

    public static AdminCorpDomainListResponse of(Corp corp) {
        return AdminCorpDomainListResponse.builder()
                .id(corp.getId())
                .corpName(corp.getCorpName())
                .corpEmailDomain(corp.getCorpEmailDomain())
                .verified(corp.getVerified())
                .build();
    }
}
