package teamkiim.koffeechat.domain.post.community.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.file.domain.File;
import teamkiim.koffeechat.domain.post.common.dto.response.TagInfoDto;
import teamkiim.koffeechat.domain.post.community.domain.CommunityPost;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityPostListResponse {

    private String id;                                // PK
    private String title;                           // 제목
    private String bodyContent;                     // 본문
    private List<TagInfoDto> tagDtoList;            //태그 리스트

    private long viewCount;                         // 조회수
    private long likeCount;                         // 좋아요 수
    private long bookmarkCount;                     // 북마크 수
    private LocalDateTime createdTime;              // 작성 시간
    private LocalDateTime modifiedTime;             // 수정 시간
    private String nickname;                        // 작성자 닉네임
    private String profileImageUrl;
    private String contentImageUrl;

    public static CommunityPostListResponse of(String postId, CommunityPost communityPost,
                                               List<TagInfoDto> tagInfoDto) {

        CommunityPostListResponse response = CommunityPostListResponse.builder()
                .id(postId)
                .title(communityPost.getTitle())
                .bodyContent(communityPost.getBodyContent())
                .tagDtoList(tagInfoDto)
                .viewCount(communityPost.getViewCount())
                .likeCount(communityPost.getLikeCount())
                .bookmarkCount(communityPost.getBookmarkCount())
                .createdTime(communityPost.getCreatedTime())
                .modifiedTime(communityPost.getModifiedTime())
                .nickname(communityPost.getMember().getNickname())
                .profileImageUrl(communityPost.getMember().getProfileImageUrl())
                .contentImageUrl(null)
                .build();

        if (!communityPost.getFileList().isEmpty()) {
            response.setImageInfo(communityPost.getFileList().get(0));
        }

        return response;
    }

    private void setImageInfo(File file) {

        this.contentImageUrl = file.getUrl();
    }
}
