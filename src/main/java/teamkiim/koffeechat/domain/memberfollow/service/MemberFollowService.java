package teamkiim.koffeechat.domain.memberfollow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.domain.memberfollow.domain.MemberFollow;
import teamkiim.koffeechat.domain.memberfollow.repository.MemberFollowRepository;
import teamkiim.koffeechat.domain.memberfollow.service.dto.MemberFollowListResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFollowService {

    private final MemberRepository memberRepository;
    private final MemberFollowRepository memberFollowRepository;

    public boolean isMemberFollowed(Member member, Member followingMember) {
        if (memberFollowRepository.findByFollowerAndFollowing(member, followingMember).isPresent()) return true;
        return false;
    }

    /**
     * 사용자 팔로우 -> 팔로우, 언팔로우
     *
     * @param memberId          팔로우 누른 회원 PK
     * @param followingMemberId member가 팔로우한 회원 PK
     * @return Long -> followingMember의 follower 수
     */
    @Transactional
    public ResponseEntity<?> memberFollow(Long memberId, Long followingMemberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        Member followingMember = memberRepository.findById(followingMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (isMemberFollowed(member, followingMember)) {  //이미 구독 중이면 -> 구독 취소
            unfollow(member, followingMember);
            member.removeFollowingCount();          //회원의 팔로잉 수 --
            followingMember.removeFollowerCount();  //회원이 구독 취소 한 회원의 팔로워 수 --
        } else {
            follow(member, followingMember);        //구독
            member.addFollowingCount();             //회원의 팔로잉 수 ++
            followingMember.addFollowerCount();     //회원이 구독한 회원의 팔로워 수 ++
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(followingMember.getFollowerCount());
    }

    /**
     * 팔로우
     */
    @Transactional
    public void follow(Member member, Member following) {
        MemberFollow follow = MemberFollow.createFollow(member, following);
        memberFollowRepository.save(follow);
    }

    /**
     * 언팔로우
     */
    @Transactional
    public void unfollow(Member member, Member following) {
        MemberFollow memberFollow = memberFollowRepository.findByFollowerAndFollowing(member, following)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_FOLLOW_NOT_FOUND));

        memberFollowRepository.delete(memberFollow);
    }

    /**
     * 특정 회원의 팔로워 리스트 조회
     *
     * @param memberId 조회할 대상 회원 PK
     * @param loginMemberId 로그인 회원 PK
     * @param page     페이지 번호
     * @param size     페이지 당 조회할 데이터 수
     * @return List<MemberFollowListResponse>
     */
    public ResponseEntity<?> followerList(Long memberId, Long loginMemberId, int page, int size) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));

        List<Member> followerList = memberFollowRepository.findFollowersByFollowingId(member, pageRequest).getContent();
        List<MemberFollowListResponse> memberFollowListResponseList=new ArrayList<>();

        for (Member follower : followerList) {
            boolean isFollowedByLoginMember=false;  //로그인한 사용자가 팔로우하는 회원인지
            boolean isLoginMember=false;            //팔로워 목록에 로그인한 사용자가 있는 경우
            if (loginMemberId != null) {
                Member loginMember = memberRepository.findById(loginMemberId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
                isFollowedByLoginMember=memberFollowRepository.existsByFollowerAndFollowing(loginMember, follower);
                if(follower.equals(loginMember)) isLoginMember=true;
            }
            memberFollowListResponseList.add(MemberFollowListResponse.of(follower, isFollowedByLoginMember, isLoginMember));
        }

        return ResponseEntity.ok(memberFollowListResponseList);

    }

    /**
     * 특정 회원의 팔로잉 리스트 조회
     *
     * @param memberId 조회할 대상 회원 PK
     * @param loginMemberId 로그인 회원 PK
     * @param page     페이지 번호
     * @param size     페이지 당 조회할 데이터 수
     * @return List<MemberFollowListResponse>
     */
    public ResponseEntity<?> followingList(Long memberId, Long loginMemberId, int page, int size) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));

        List<Member> followingList = memberFollowRepository.findFollowingsByFollowerId(member, pageRequest).getContent();
        List<MemberFollowListResponse> memberFollowListResponseList=new ArrayList<>();

        for (Member following : followingList) {
            boolean isFollowedByLoginMember=false;  //로그인한 사용자가 팔로우하는 회원인지
            boolean isLoginMember=false;            //팔로워 목록에 로그인한 사용자가 있는 경우
            if (loginMemberId != null) {
                Member loginMember = memberRepository.findById(loginMemberId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
                isFollowedByLoginMember=memberFollowRepository.existsByFollowerAndFollowing(loginMember, following);
                if(following.equals(loginMember)) isLoginMember=true;
            }
            memberFollowListResponseList.add(MemberFollowListResponse.of(following, isFollowedByLoginMember, isLoginMember));
        }

        return ResponseEntity.ok(memberFollowListResponseList);

    }
}
