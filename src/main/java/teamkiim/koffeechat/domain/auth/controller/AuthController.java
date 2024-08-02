package teamkiim.koffeechat.domain.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.domain.auth.controller.dto.LoginRequest;
import teamkiim.koffeechat.domain.auth.controller.dto.SignUpRequest;
import teamkiim.koffeechat.domain.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
@Tag(name = "인증 API")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @AuthApiDocument.SignUpApiDoc
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest){

        return authService.signUp(signUpRequest.toServiceRequest());
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @AuthApiDocument.loginApiDoc
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){

        return authService.login(loginRequest.toServiceRequest(), response);
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    @AuthApiDocument.logoutApiDoc
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){

        return authService.logout(request, response);
    }

}
