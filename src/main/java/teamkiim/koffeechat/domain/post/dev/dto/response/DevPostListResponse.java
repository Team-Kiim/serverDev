package teamkiim.koffeechat.domain.post.dev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamkiim.koffeechat.domain.file.domain.File;
import teamkiim.koffeechat.domain.post.common.dto.response.TagInfoDto;
import teamkiim.koffeechat.domain.post.dev.domain.DevPost;
import teamkiim.koffeechat.domain.post.dev.domain.SkillCategory;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@AllArgsConstructor
@Builder
public class DevPostListResponse {

    private String id;                                // PK
    private String title;                           // 제목
    private String bodyContent;                     // 본문
    private List<TagInfoDto> tagDtoList;            //태그 리스트
    private List<SkillCategory> skillCategoryList;  //기술 카테고리 리스트

    private long viewCount;                         // 조회수
    private long likeCount;                         // 좋아요 수
    private long bookmarkCount;                     // 북마크 수

    private LocalDateTime createdTime;              // 작성 시간
    private LocalDateTime modifiedTime;

    private String nickname;                        // 작성자 닉네임
    private String profileImagePath;                // 작성자 프로필 이미지 path
    private String profileImageName;                // 작성자 프로필 이미지 이름

    private String imagePath;                       // 이미지 경로
    private String imageName;                       // 이미지 이름

    public static DevPostListResponse of(String postId, DevPost devPost, List<TagInfoDto> tagInfoDto) {

        DevPostListResponse response = DevPostListResponse.builder()
                .id(postId)
                .title(devPost.getTitle())
                .bodyContent(devPost.getBodyContent())
                .tagDtoList(tagInfoDto)
                .skillCategoryList(devPost.getSkillCategoryList())
                .viewCount(devPost.getViewCount())
                .likeCount(devPost.getLikeCount())
                .bookmarkCount(devPost.getBookmarkCount())
                .createdTime(devPost.getCreatedTime())
                .modifiedTime(devPost.getModifiedTime())
                .nickname(devPost.getMember().getNickname())
                .profileImagePath(devPost.getMember().getProfileImagePath())
                .profileImageName(devPost.getMember().getProfileImageName())
                .imagePath(null)
                .imageName(null)
                .build();

        if (!devPost.getFileList().isEmpty()) {
            response.setImageInfo(devPost.getFileList().get(0));
        }

        return response;
    }

    private void setImageInfo(File file) {

        this.imagePath = file.getPath();
        this.imageName = file.getName();
    }
}
