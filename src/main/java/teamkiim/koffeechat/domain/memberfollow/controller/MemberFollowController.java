package teamkiim.koffeechat.domain.memberfollow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.global.Auth;
import teamkiim.koffeechat.domain.memberfollow.service.MemberFollowService;
import teamkiim.koffeechat.domain.memberfollow.service.dto.MemberFollowListResponse;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-follow")
@Tag(name = "회원 FOLLOW API")
public class MemberFollowController {

    private final MemberFollowService memberFollowService;

    /**
     * 팔로우
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/{memberId}")
    @MemberFollowApiDocument.MemberFollow
    public ResponseEntity<?> memberFollow(@PathVariable("memberId") Long followingMemberId, HttpServletRequest request) {
        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return memberFollowService.memberFollow(memberId, followingMemberId);
    }

    /**
     * 사용자 follower list 확인
     */
    @Auth(role={})
    @GetMapping("/follower-list/{memberId}")
    @MemberFollowApiDocument.FollowerList
    public ResponseEntity<?> followerList(@PathVariable("memberId") Long memberId, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest request) {

        Long loginMemberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return memberFollowService.followerList(memberId, loginMemberId, page, size);  //팔로워 목록 확인 대상 member id
    }

    /**
     * 사용자 following list 확인
     */
    @Auth(role={})
    @GetMapping("/following-list/{memberId}")
    @MemberFollowApiDocument.FollowingList
    public ResponseEntity<?> followingList(@PathVariable("memberId") Long memberId, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletRequest request) {

        Long loginMemberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));

        return memberFollowService.followingList(memberId, loginMemberId, page, size);  //팔로잉 목록 확인 대상 -> member id
    }

}
