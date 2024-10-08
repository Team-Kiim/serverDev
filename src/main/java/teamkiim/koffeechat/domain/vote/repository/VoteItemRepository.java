package teamkiim.koffeechat.domain.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamkiim.koffeechat.domain.post.common.domain.Post;
import teamkiim.koffeechat.domain.vote.domain.Vote;
import teamkiim.koffeechat.domain.vote.domain.VoteItem;

import java.util.List;
import java.util.Optional;

public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

    @Query("SELECT vi.vote FROM VoteItem vi WHERE vi.id= :voteItemId")
    Optional<Vote> findVoteByVoteItemId(@Param("voteItemId") Long voteItemId);

    List<VoteItem> findByVote(Vote vote);

    @Query("SELECT vi FROM VoteItem vi WHERE vi.vote.post=:post AND vi.id IN :items")
    List<VoteItem> findAllByPostAndIds(@Param("post") Post post, @Param("items") List<Long> items);
}
