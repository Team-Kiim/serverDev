package teamkiim.koffeechat.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamkiim.koffeechat.domain.file.dto.response.ProfileImageInfoResponse;
import teamkiim.koffeechat.domain.file.service.FileStorageControlService;
import teamkiim.koffeechat.domain.member.dto.response.MemberInfoResponse;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.dto.request.EnrollSkillCategoryServiceRequest;
import teamkiim.koffeechat.domain.member.dto.request.ModifyProfileServiceRequest;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.domain.memberfollow.service.MemberFollowService;
import teamkiim.koffeechat.domain.post.dev.domain.SkillCategory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileStorageControlService fileStorageControlService;
    private final MemberFollowService memberFollowService;

    /**
     * 회원 정보 수정
     * @param modifyProfileServiceRequest 회원 정보 수정 dto
     * @param memberId 사용자 PK
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> modifyProfile(ModifyProfileServiceRequest modifyProfileServiceRequest, Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        member.modify(modifyProfileServiceRequest.getNickname(), modifyProfileServiceRequest.getMemberRole());

        return ResponseEntity.status(HttpStatus.CREATED).body("회원 정보 수정 완료");
    }

    /**
     * 관심 기술 등록
     * @param memberId 사용자 PK
     * @param enrollSkillCategoryServiceRequestList 관심 기술 dto
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> enrollSkillCategory(Long memberId,
                                                 List<EnrollSkillCategoryServiceRequest> enrollSkillCategoryServiceRequestList){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<SkillCategory> skillCategoryList = enrollSkillCategoryServiceRequestList.stream()
                .map(enrollSkillCategoryServiceRequest -> enrollSkillCategoryServiceRequest.combine())
                        .collect(Collectors.toList());

        member.enrollSkillCategory(skillCategoryList);

        return ResponseEntity.status(HttpStatus.CREATED).body("관심 기술 설정 완료");
    }

    /**
     * 프로필 이미지 등록
     * @param memberId 회원 PK
     * @param multipartFile 실제 파일
     * @return ok
     */
    @Transactional
    public ResponseEntity<?> enrollProfileImage(Long memberId, MultipartFile multipartFile){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        ProfileImageInfoResponse response = fileStorageControlService.saveFile(member, multipartFile);

        member.enrollProfileImage(response.getProfileImageName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 회원 프로필 조회
     * @param profileMemberId 조회한 대상 회원의 PK
     * @param loginMemberId 로그인한 회원 (현재 요청을 보낸) 의 PK
     * @return
     */
    public ResponseEntity<?> findMemberInfo(Long profileMemberId, Long loginMemberId){

        Member member;
        boolean isLoginMemberInfo;      //로그인 한 사용자의 프로필인지
        Boolean isFollowingMember=null;  //로그인 한 사용자가 팔로우하는 사용자의 프로필인지

        if(profileMemberId == null){  // 마이페이지로의 접근
            member = memberRepository.findById(loginMemberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            isLoginMemberInfo = true;
        }
        else{  // 프로필로의 접근
            member = memberRepository.findById(profileMemberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            isLoginMemberInfo = (member.getId() == loginMemberId) ? true : false;

            if (!isLoginMemberInfo) {  //다른 회원의 프로필인 경우
                Member loginMember= memberRepository.findById(loginMemberId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
                isFollowingMember = memberFollowService.isMemberFollowed(loginMember, member);
            }
        }
        
        MemberInfoResponse response = MemberInfoResponse.of(member, isLoginMemberInfo, isFollowingMember);

        return ResponseEntity.ok(response);
    }
}
