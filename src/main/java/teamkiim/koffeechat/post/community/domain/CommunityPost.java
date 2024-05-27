package teamkiim.koffeechat.post.community.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import teamkiim.koffeechat.comment.domain.Comment;
import teamkiim.koffeechat.member.domain.Member;
import teamkiim.koffeechat.post.domain.Post;
import teamkiim.koffeechat.post.domain.PostCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CommunityPost extends Post {

    @Builder
    public CommunityPost(Member member, PostCategory postCategory, String title, String bodyContent,
                         Long viewCount, Long likeCount, LocalDateTime createdTime, LocalDateTime modifiedTime) {

        super(member, postCategory, title, bodyContent, viewCount, likeCount, createdTime, modifiedTime);
    }

    //== 비지니스 로직 ==//

    /**
     * CommunityPost 완성
     * @param title 제목
     * @param bodyContent 본문
     * @param createdTime 작성 시간
     */
    public void completeCommunityPost(String title, String bodyContent, LocalDateTime createdTime){

        complete(PostCategory.COMMUNITY, title, bodyContent, createdTime);
    }

    /**
     * CommunityPost 수정
     * @param title 제목
     * @param bodyContent 본문
     * @param modifiedTime 수정 시간
     */
    public void modify(String title, String bodyContent, LocalDateTime modifiedTime){

        modify(title, bodyContent, modifiedTime);
    }

}
