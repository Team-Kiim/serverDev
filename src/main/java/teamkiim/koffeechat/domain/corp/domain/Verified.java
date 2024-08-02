package teamkiim.koffeechat.domain.corp.domain;

import lombok.Getter;

@Getter
public enum Verified {

    WAITING("대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String status;

    Verified(String status) {
        this.status = status;
    }
}
