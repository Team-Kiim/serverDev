package teamkiim.koffeechat.domain.corp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamkiim.koffeechat.domain.corp.controller.dto.request.*;
import teamkiim.koffeechat.domain.corp.service.CorpService;
import teamkiim.koffeechat.domain.corp.service.dto.response.CorpDomainResponse;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corp")
@Tag(name="현직자 인증 API")
public class CorpController {

    private final CorpService corpService;

    /**
     * 도메인 승인 요청 -> 대기 상태로 관리자에게 검증 요청
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/domain")
    @CorpApiDocument.RequestCorpDomain
    public ResponseEntity<?> requestCorpDomain(@Valid @RequestBody CorpDomainRequest corpDomainRequest) {
        String response= corpService.saveCorpRequest(corpDomainRequest.getCorpName(), corpDomainRequest.getCorpEmailDomain());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 회사 도메인 검색
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/find-corp-mail")
    @CorpApiDocument.FindCorpDomain
    public ResponseEntity<?> findCorpDomain(@Valid @RequestBody FindCorpNameRequest corpNameRequest) {

        //회사 이름으로 검색
        List<CorpDomainResponse> corpList= corpService.findCorpRequest(corpNameRequest.getCorpName());

        if (corpList.isEmpty()) {
            return ResponseEntity.ok().body("찾으시는 회사명이 없으신가요? 직접 입력해주세요.");
        }

        return ResponseEntity.ok().body(corpList);
    }

    @AuthenticatedMemberPrincipal
    @PostMapping("/find-corp-name")
    @CorpApiDocument.FindCorpDomain
    public ResponseEntity<?> findCorpName(@Valid @RequestBody FindCorpDomainRequest corpDomainRequest) {

        //회사 도메인으로 검색
        List<CorpDomainResponse> corpList= corpService.findCorpNameRequest(corpDomainRequest.getCorpEmailDomain());

        if (corpList.isEmpty()) {
            return ResponseEntity.ok().body("찾으시는 회사 도메인이 없으신가요? 직접 입력해주세요.");
        }

        return ResponseEntity.ok().body(corpList);
    }

    /**
     * 현직자 인증 시 이메일 전송
     * 이메일 보내고 인증까지 받고나서 admin 최종 승인받기 (승인 목록)
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/send-corp-code")
    @CorpApiDocument.SendCorpEmail
    public ResponseEntity<?> sendCorpEmail(@Valid @RequestBody CorpAuthRequest corpRequest, HttpServletRequest request ){
        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));
        String response= corpService.sendCorpEmailAuthCode(corpRequest.getCorpName(), corpRequest.getEmail(), memberId);

        return ResponseEntity.ok().body(response);
    }

    /**
     * 회사 이메일 인증 코드 확인
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/check-corp-code")
    @CorpApiDocument.CheckCorpCode
    public ResponseEntity<?> checkCorpCode(@Valid @RequestBody CorpAuthCodeCheckRequest codeCheckRequest , HttpServletRequest request ){
        Long memberId = Long.valueOf(String.valueOf(request.getAttribute("authenticatedMemberPK")));
        String response= corpService.checkCorpEmailAuthCode(memberId, codeCheckRequest.getCorpName(), codeCheckRequest.getEmail(), codeCheckRequest.getCode());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
