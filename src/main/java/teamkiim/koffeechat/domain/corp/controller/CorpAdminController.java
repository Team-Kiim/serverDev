package teamkiim.koffeechat.domain.corp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.corp.controller.admindto.request.EnrollCorpDomainRequest;
import teamkiim.koffeechat.domain.corp.controller.admindto.request.ManageCorpDomainRequest;
import teamkiim.koffeechat.domain.corp.controller.admindto.response.AdminCorpDomainListResponse;
import teamkiim.koffeechat.domain.corp.domain.Verified;
import teamkiim.koffeechat.domain.corp.service.CorpAdminService;
import teamkiim.koffeechat.global.Auth;

import java.util.List;

import static teamkiim.koffeechat.global.Auth.MemberRole.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/corp")
@Tag(name = "[ADMIN] 현직자 인증 도메인 관리 API")
public class CorpAdminController {

    private final CorpAdminService corpAdminService;

    /**
     * 도메인 등록
     */
    @Auth(role = ADMIN)
    @PostMapping("/enroll")
    @CorpAdminApiDocument.EnrollCorpDomain
    public ResponseEntity<?> enrollCorpDomain(@Valid @RequestBody EnrollCorpDomainRequest enrollCorpDomainRequest) {
        corpAdminService.saveCorpDomain(enrollCorpDomainRequest.getCorpName(), enrollCorpDomainRequest.getCorpEmailDomain());

        return ResponseEntity.status(HttpStatus.CREATED).body("도메인 등록 완료.");
    }

    /**
     * 도메인 상태 관리 : 승인, 거절
     */
    @Auth(role = ADMIN)
    @PostMapping("/modify/{corpId}")
    @CorpAdminApiDocument.ManageCorpDomain
    public ResponseEntity<?> manageCorpDomain(@PathVariable("corpId") Long corpId, @Valid @RequestBody Verified verifiedRequest) {
        Verified verified = corpAdminService.modifyCorpDomain(corpId, verifiedRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(verified);
    }

    /**
     * 도메인 삭제
     */
    @Auth(role = ADMIN)
    @PostMapping("/delete/{corpId}")
    @CorpAdminApiDocument.DeleteCorpDomain
    public ResponseEntity<?> deleteCorpDomain(@PathVariable("corpId") Long corpId) {
        corpAdminService.deleteCorpDomain(corpId);

        return ResponseEntity.status(HttpStatus.CREATED).body("도메인 삭제 완료.");
    }

    /**
     * 도메인 목록 출력
     */
    @Auth(role = ADMIN)
    @PostMapping("/list")
    @CorpAdminApiDocument.List
    public ResponseEntity<?> list() {
        List<AdminCorpDomainListResponse> responseList = corpAdminService.list();

        return ResponseEntity.ok().body(responseList);
    }

    /**
     * 도메인 검색 : 회사 이름, 이메일
     */
    @Auth(role = ADMIN)
    @PostMapping("/search")
    @CorpAdminApiDocument.Search
    public ResponseEntity<?> search(@RequestBody String keyword) {
        List<AdminCorpDomainListResponse> responseList = corpAdminService.search(keyword);

        return ResponseEntity.ok().body(responseList);
    }


}
