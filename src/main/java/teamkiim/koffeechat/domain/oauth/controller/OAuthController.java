package teamkiim.koffeechat.domain.oauth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamkiim.koffeechat.domain.oauth.controller.dto.GoogleAuthRequest;
import teamkiim.koffeechat.domain.oauth.controller.dto.KakaoAuthRequest;
import teamkiim.koffeechat.domain.oauth.controller.dto.SaveSocialLoginMemberInfoRequest;
import teamkiim.koffeechat.domain.oauth.controller.dto.SocialAccessTokenRequest;
import teamkiim.koffeechat.domain.oauth.service.OAuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Tag(name = "소셜 인증 API")
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * 카카오 인증 코드로 카카오 서버에 access, refresh 토큰 요청
     */
    @PostMapping("/get-kakao-code")
    @OAuthApiDocument.GetKakaoAccessTokenFromCode
    public ResponseEntity<?> getKakaoAccessTokenFromCode(@Valid @RequestBody KakaoAuthRequest kakaoAuthRequest) {

        return oAuthService.getKakaoJWT(kakaoAuthRequest.toServiceRequest());
    }

    /**
     * 구글 인증 코드로 구글 서버에 access, refresh 토큰 요청
     */
    @PostMapping("/get-google-code")
    @OAuthApiDocument.GetGoogleAccessTokenFromCode
    public ResponseEntity<?> getGoogleAccessTokenFromCode(@Valid @RequestBody GoogleAuthRequest googleAuthRequest) {

        return oAuthService.getGoogleJWT(googleAuthRequest.toServiceRequest());
    }

    /**
     * 소셜 로그인 인증 서버로부터 발급받은 Access 토큰을 이용하여 소셜 로그인 회원의 회원정보 조회
     */
    @PostMapping("/get-member-info")
    @OAuthApiDocument.GetAccessTokenAndGetMemberInfo
    public ResponseEntity<?> getAccessTokenAndGetMemberInfo(@Valid @RequestBody SocialAccessTokenRequest socialAccessTokenRequest) {

        ResponseEntity<?> response;

        switch (socialAccessTokenRequest.getSocialLoginType()) {
            case NAVER -> response = oAuthService.getMemberInfoFromNaver(socialAccessTokenRequest.getAccessToken());
            case KAKAO -> response = oAuthService.getMemberInfoFromKakao(socialAccessTokenRequest.getAccessToken());
            case GOOGLE -> response = oAuthService.getMemberInfoFromGoogle(socialAccessTokenRequest.getAccessToken());
            default -> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시용자 정보를 불러오는 중 에러가 발생했습니다.");
        }

        return response;
    }

    /**
     * 소셜 로그인 회원의 로그인/회원가입
     */
    @PostMapping("/login")
    @OAuthApiDocument.SocialLogin
    public ResponseEntity<?> socialLogin(@Valid @RequestBody SaveSocialLoginMemberInfoRequest saveSocialLoginMemberInfoRequest,
                                         HttpServletResponse response) {

        return oAuthService.loginOrSignUpSocialMember(saveSocialLoginMemberInfoRequest.toServiceRequest(), response);
    }
}
