package teamkiim.koffeechat.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import teamkiim.koffeechat.post.community.CommunityPost;

import java.time.LocalDateTime;

@Getter
public class CommunityPostViewResponseDto {
    private Long id;
    //user
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String bodyContent;
    private Long viewCount;
    private Long likeCount;
    private LocalDateTime createdTime;  // 작성 시간
    private LocalDateTime modifiedTime;

    //skillCategoryList
    //fileList

    //comments

    public void set(CommunityPost post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.bodyContent = post.getBodyContent();
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikeCount();
        this.createdTime = post.getCreatedTime();
        this.modifiedTime = post.getModifiedTime();
    }
}
