package teamkiim.koffeechat.domain.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import teamkiim.koffeechat.domain.notification.domain.Notification;
import teamkiim.koffeechat.domain.notification.domain.NotificationType;
import teamkiim.koffeechat.domain.post.common.domain.PostCategory;

@Getter
@Builder
public class CommentNotificationResponse {

    private String receiverId;  //알림을 받는 회원
    private long unreadNotifications;  //읽지 않은 알림 갯수

    private String senderId;  //알림 내용에 포함될 회원
    private String senderNickname;
    private String senderProfileImageUrl;

    private String title;

    private String content;

    private String url;  //comment url에는 게시글 pk

    private String commentId;

    private PostCategory postCategory;

    private boolean isRead;

    private NotificationType notificationType;

    private LocalDateTime createdTime;

    public static CommentNotificationResponse of(String receiverId, String senderId, String postId, String commentId,
                                                 Notification notification) {
        return CommentNotificationResponse.builder()
                .receiverId(receiverId)
                .unreadNotifications(notification.getReceiver().getUnreadNotificationCount())
                .senderId(senderId)
                .senderNickname(notification.getSender().getNickname())
                .senderProfileImageUrl(notification.getSender().getProfileImageUrl())
                .title(notification.getTitle())
                .content(notification.getContent())
                .url(postId)
                .postCategory(notification.getPostType())
                .commentId(commentId)
                .isRead(false)
                .notificationType(notification.getNotificationType())
                .createdTime(notification.getCreatedTime())
                .build();
    }
}
