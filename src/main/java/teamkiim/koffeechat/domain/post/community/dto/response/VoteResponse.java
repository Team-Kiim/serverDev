package teamkiim.koffeechat.domain.post.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamkiim.koffeechat.domain.vote.domain.Vote;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteResponse {

    private String title;                                   //투표 제목
    private List<VoteItemResponse> voteItemResponseList;    //투표 항목들 dto list
    private boolean isMemberVoted;

    public static VoteResponse of(Vote vote, boolean isMemberVoted) {

        List<VoteItemResponse> voteItemResponseList = vote.getVoteItems().stream()
                .map(voteItem-> VoteItemResponse.of(voteItem, isMemberVoted))
                .collect(Collectors.toList());

        return VoteResponse.builder()
                .title(vote.getTitle())
                .voteItemResponseList(voteItemResponseList)
                .isMemberVoted(isMemberVoted)
                .build();
    }

}
