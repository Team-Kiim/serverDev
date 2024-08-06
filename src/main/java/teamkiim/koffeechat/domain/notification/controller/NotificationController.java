package teamkiim.koffeechat.domain.notification.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import teamkiim.koffeechat.domain.notification.service.NotificationService;
import teamkiim.koffeechat.domain.notification.service.dto.response.NotificationListResponse;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Tag(name = "알림 API")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * SSE 연결 설정
     */
    @AuthenticatedMemberPrincipal
    @GetMapping("/subscribe")
    public SseEmitter subscribe(HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return notificationService.connectNotification(memberId);
    }

    /**
     * 알림 목록 조회
     */
    @AuthenticatedMemberPrincipal
    @GetMapping("/list")
    public ResponseEntity<?> showList(@RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest request) {

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        List<NotificationListResponse> responseList = notificationService.list(memberId, page, size);

        return ResponseEntity.ok(responseList);
    }
}
