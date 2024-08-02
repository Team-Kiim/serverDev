package teamkiim.koffeechat.domain.vote.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.vote.controller.dto.SaveVoteRecordRequest;
import teamkiim.koffeechat.domain.vote.service.VoteService;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
@Tag(name="투표 API")
public class VoteController {

    private final VoteService voteService;

    /**
     * 투표
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/{postId}/votes")
    @VoteApiDocument.SaveVoteRecord
    public ResponseEntity<?> saveVoteRecord(@PathVariable("postId") Long postId,
                                            @Valid @RequestBody SaveVoteRecordRequest saveVoteRecordRequest, HttpServletRequest request){

        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return voteService.saveVoteRecord(postId, saveVoteRecordRequest, memberId);
    }
}
