package teamkiim.koffeechat.domain.corp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Corp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corp_id")
    private Long id;

    private String corpName;               //회사 이름

    private String corpEmailDomain;        //회사 이메일 도메인

    @Enumerated(EnumType.STRING)
    private Verified verified;               //이메일 도메인 검증 상태

    /**
     * 생성자
     */
    public Corp(String corpName, String corpEmailDomain, Verified verified) {
        this.corpName=corpName;
        this.corpEmailDomain=corpEmailDomain;
        this.verified=verified;
    }

    //== 비즈니스 로직 ==//
    public void statusModify(Verified verified) {
        this.verified = verified;
    }
}
