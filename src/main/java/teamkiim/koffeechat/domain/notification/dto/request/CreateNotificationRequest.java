package teamkiim.koffeechat.domain.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import teamkiim.koffeechat.domain.admin.corp.domain.VerifyStatus;
import teamkiim.koffeechat.domain.comment.domain.Comment;
import teamkiim.koffeechat.domain.corp.domain.Corp;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.notification.domain.Notification;
import teamkiim.koffeechat.domain.notification.domain.NotificationType;
import teamkiim.koffeechat.domain.post.common.domain.Post;
import teamkiim.koffeechat.domain.post.common.domain.PostCategory;

@Getter
@Builder
@Schema(description = "알림 생성 Request")
public class CreateNotificationRequest {

    private Member sender;

    private String title;

    private String content;

    private Long urlPK;  //urlPK

    private Long commentId;  //commentId

    private PostCategory postType; //postType

    private boolean isRead;

    private NotificationType notificationType;

    public static CreateNotificationRequest ofForPost(NotificationType notificationType, Member sender, Post post) {
        return CreateNotificationRequest.builder()
                .sender(sender)
                .urlPK(post.getId())
                .title(post.getTitle())
                .postType(post.getPostCategory())
                .notificationType(notificationType)
                .build();
    }

    public static CreateNotificationRequest ofForTechPost(NotificationType notificationType, String skill, Post post) {
        return CreateNotificationRequest.builder()
                .title(skill)
                .content(post.getTitle())
                .urlPK(post.getId())
                .postType(post.getPostCategory())
                .notificationType(notificationType)
                .build();
    }

    public static CreateNotificationRequest ofForComment(NotificationType notificationType, Member sender, Post post,
                                                         Comment comment) {
        return CreateNotificationRequest.builder()
                .sender(sender)
                .urlPK(post.getId())
                .title(post.getTitle())
                .content(comment.getContent())
                .commentId(comment.getId())
                .postType(post.getPostCategory())
                .notificationType(notificationType)
                .build();
    }

    public static CreateNotificationRequest ofForFollow(NotificationType notificationType, Member sender) {
        return CreateNotificationRequest.builder()
                .sender(sender)
                .notificationType(notificationType)
                .build();
    }

    public static CreateNotificationRequest ofForCorp(NotificationType notificationType, Corp corp,
                                                      VerifyStatus verifyStatus) {
        return CreateNotificationRequest.builder()
                .urlPK(corp.getId())
                .title(corp.getName())
                .content(verifyStatus.getName())
                .notificationType(notificationType)
                .build();
    }

    public Notification toEntity(String eventId, Member receiver) {
        return Notification.builder()
                .eventId(eventId)
                .receiver(receiver)
                .sender(sender)
                .urlPK(urlPK)
                .title(title)
                .content(content)
                .commentId(commentId)
                .postType(postType)
                .notificationType(notificationType)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
