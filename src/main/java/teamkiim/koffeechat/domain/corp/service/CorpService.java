package teamkiim.koffeechat.domain.corp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamkiim.koffeechat.domain.corp.domain.Corp;
import teamkiim.koffeechat.domain.corp.domain.Verified;
import teamkiim.koffeechat.domain.corp.repository.CorpRepository;
import teamkiim.koffeechat.domain.corp.service.dto.response.CorpDomainResponse;
import teamkiim.koffeechat.domain.email.domain.EmailAuth;
import teamkiim.koffeechat.domain.email.repository.EmailAuthRepository;
import teamkiim.koffeechat.domain.member.domain.Member;
import teamkiim.koffeechat.domain.member.repository.MemberRepository;
import teamkiim.koffeechat.global.exception.CustomException;
import teamkiim.koffeechat.global.exception.ErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CorpService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final CorpRepository corpRepository;
    private final EmailAuthRepository emailAuthRepository;

    @Value("${email-address}")
    private String hostAddress;                                     // 메일을 보내는 호스트 이메일 주소

    /**
     * 회사 도메인 등록 요청 대기 상태로 저장
     * 거절된 도메인에 대해 요청하면 -> 요청 거절
     */
    @Transactional
    public String saveCorpRequest(String corpName, String corpDomain) {

        Optional<Corp> corp = corpRepository.findByCorpNameAndCorpEmailDomain(corpName, corpDomain);

        if (corp.isPresent()) {  // 이미 있는 요청
            if (corp.get().getVerified().equals(Verified.REJECTED)) return "승인 거절된 도메인입니다. 이메일 확인 후 문의해주세요.";
            if(corp.get().getVerified().equals(Verified.APPROVED)) return "이미 존재하는 도메인입니다.";
        } else {                 // 새로운 요청 -> 요청 생성
            Corp corpRequest = new Corp(corpName, corpDomain, Verified.WAITING);
            corpRepository.save(corpRequest);
        }

        return "도메인 등록이 요청되었습니다. 승인이 될 때까지 기다려 주세요.";
    }

    /**
     * 회사 도메인 검색 : 회사 검색, 도메인 검색
     *
     * @param corpName 회사 이름
     */
    public List<CorpDomainResponse> findCorpRequest(String corpName) {

        List<Corp> corpList = corpRepository.findAllByCorpNameContainingIgnoreCaseAndVerified(corpName, Verified.APPROVED);

        List<CorpDomainResponse> responseList = corpList.stream().map(CorpDomainResponse::of).collect(Collectors.toList());
        return responseList;
    }

    /**
     * @param corpDomain 회사 도메인
     */
    public List<CorpDomainResponse> findCorpNameRequest(String corpDomain) {

        List<Corp> corpList = corpRepository.findAllByCorpEmailDomainContainingIgnoreCaseAndVerified(corpDomain, Verified.APPROVED);

        List<CorpDomainResponse> responseList = corpList.stream().map(CorpDomainResponse::of).collect(Collectors.toList());

        return responseList;
    }

    /**
     * 현직자 인증 시 이메일 전송
     *
     * @param email
     * @param memberId
     */
    @Transactional
    public String sendCorpEmailAuthCode(String corpName, String email, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        String domain = email.substring(email.indexOf('@') + 1);
        Optional<Corp> corp = corpRepository.findByCorpEmailDomain(domain);
        if (corp.isEmpty()) {
            Corp newCorp = new Corp(corpName, domain, Verified.WAITING);
            corpRepository.save(newCorp);
            return "등록되어있지 않은 회사입니다. 도메인 승인이 되면 다시 인증해주세요.";
        }

        if (corp.get().getVerified().equals(Verified.WAITING)) {
            return "등록 요청이 되어있는 도메인입니다. 도메인 승인이 되면 다시 인증해주세요.";
        }
        if (corp.get().getVerified().equals(Verified.REJECTED)) {
            return "인증할 수 없는 도메인입니다. 이메일을 확인 후 문의해주세요.";
        }

        //인증된 도메인인 경우 메일 발송
        EmailAuth emailAuth = new EmailAuth(email);
        emailAuthRepository.save(emailAuth);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            mimeMessageHelper.setFrom(hostAddress);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[KoffeChat] 이메일 인증 서비스");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<!DOCTYPE html>");
            emailBody.append("<html lang='ko'>");
            emailBody.append("<head>");
            emailBody.append("<meta charset='UTF-8'>");
            emailBody.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            emailBody.append("<title>이메일 인증</title>");
            emailBody.append("</head>");
            emailBody.append("<body style='font-family: Arial, sans-serif; background-color: #f3f4f6; margin: 0; padding: 0;'>");
            emailBody.append("<div style='max-width: 600px; margin: 40px auto; background-color: #ffffff; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); border-radius: 10px; overflow: hidden;'>");
            emailBody.append("<div style='background-color: #7c3aed; color: white; padding: 25px; text-align: center;'>");
            emailBody.append("<h1 style='margin: 0; font-size: 26px; font-weight: bold;'>KoffeeChat 이메일 인증</h1>");
            emailBody.append("</div>");
            emailBody.append("<div style='padding: 40px; text-align: center;'>");
            emailBody.append("<h2 style='color: #333; font-size: 22px; margin-top: 0;'>안녕하세요, " + member.getNickname() + " 님</h2>");
            emailBody.append("<p style='color: #555; font-size: 16px; line-height: 1.6; margin: 20px 0;'><strong>[KoffeeChat]</strong> 이메일 인증 서비스 입니다.</p>");
            emailBody.append("<p style='color: #555; font-size: 16px; line-height: 1.6;'>홈페이지로 돌아가서 다음 인증코드를 입력해주세요.</p>");
            emailBody.append("<div style='display: inline-block; margin: 30px 0; padding: 15px 30px; background-color: #e3f2fd; border: 2px solid #7c3aed; border-radius: 5px; font-size: 20px; letter-spacing: 2px; color: #7c3aed; text-align: center;'>" + emailAuth.getCode() + "</div>");
            emailBody.append("</div>");
            emailBody.append("<div style='background-color: #f1f3f4; color: #555; text-align: center; padding: 20px; font-size: 12px; border-top: 1px solid #ddd;'>");
            emailBody.append("<p>&copy; 2024 KoffeChat. All rights reserved.</p>");
            emailBody.append("</div>");
            emailBody.append("</div>");
            emailBody.append("</body>");
            emailBody.append("</html>");

            mimeMessageHelper.setText(emailBody.toString(), true);          //html형식으로 설정

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.CANNOT_SEND_EMAIL);
        }

        return "이메일 전송 완료, 이메일을 확인해주세요";
    }

    /**
     * 현직자 인증 시 이메일 인증코드 확인
     *
     * @param email 회원의 회사 이메일
     * @param code  회사 이메일 인증 코드
     */
    @Transactional
    public String checkCorpEmailAuthCode(Long memberId, String corpName, String email, String code) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_CODE_NOT_MATCH));

        emailAuthRepository.delete(emailAuth);

        member.authCorp(corpName, email);

        return "인증 되었습니다.";
    }
}
