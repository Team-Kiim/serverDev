package teamkiim.koffeechat.post.dev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamkiim.koffeechat.file.domain.File;
import teamkiim.koffeechat.post.dev.domain.DevPost;
import teamkiim.koffeechat.post.domain.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class DevPostListResponse {

    private Long id;                                // PK
    private String title;                           // 제목
    private Long viewCount;                         // 조회수
    private Long likeCount;                         // 좋아요 수
    private LocalDateTime createdTime;              // 작성 시간
    private String nickname;                        // 작성자 닉네임

    private String imagePath;                       // 이미지 경로
    private String imageName;                       // 이미지 이름

    public static DevPostListResponse of(DevPost devPost){

        DevPostListResponse response = DevPostListResponse.builder()
                .id(devPost.getId())
                .title(devPost.getTitle())
                .viewCount(devPost.getViewCount())
                .likeCount(devPost.getLikeCount())
                .createdTime(devPost.getCreatedTime())
                .nickname(devPost.getMember().getNickname())
                .imagePath(null)
                .imageName(null)
                .build();

        if(!devPost.getFileList().isEmpty()){
            response.setImageInfo(devPost.getFileList().get(0));
        }

        return response;
    }

    private void setImageInfo(File file){

        this.imagePath = file.getPath();
        this.imageName = file.getName();
    }
}