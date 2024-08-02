package teamkiim.koffeechat.domain.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import teamkiim.koffeechat.domain.oauth.dto.response.SocialMemberInfoResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "OAuth 인증 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OAuthApiDocument {

    /**
     * 카카오 인증 코드로 카카오 서버에 access, refresh 토큰 요청
     */
    @Operation(summary = "카카오 인증 코드로 엑세스 토큰 발급", description = "카카오 인증 코드로 access, refresh 토큰을 발급받는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JSONObject.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "외부 API로 부터 응답으로 받은 JSON 데이터에 문제가 있을 경우",
                            value = "{\"code\": 500, \"message\": \"소셜 로그인 사용자 정보 json 파싱 에러\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GetKakaoAccessTokenFromCode { }

    /**
     * 구글 인증 코드로 구글 서버에 access, refresh 토큰 요청
     */
    @Operation(summary = "구글 인증 코드로 엑세스 토큰 발급", description = "카카오 인증 코드로 access, refresh 토큰을 발급받는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JSONObject.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "외부 API로 부터 응답으로 받은 JSON 데이터에 문제가 있을 경우",
                            value = "{\"code\": 500, \"message\": \"소셜 로그인 사용자 정보 json 파싱 에러\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GetGoogleAccessTokenFromCode { }

    /**
     * 소셜 로그인 인증 서버로부터 발급받은 Access 토큰을 이용하여 소셜 로그인 회원의 회원정보 조회
     */
    @Operation(summary = "소셜 로그인 회원 정보 조회", description = "소셜 로그인 서버 엑세스 토큰을 이용해 사용자 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SocialMemberInfoResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "외부 API로 부터 응답으로 받은 JSON 데이터에 문제가 있을 경우",
                            value = "{\"code\": 500, \"message\": \"소셜 로그인 사용자 정보 json 파싱 에러\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GetAccessTokenAndGetMemberInfo { }

    /**
     * 소셜 로그인 회원의 로그인/회원가입
     */
    @Operation(summary = "소셜 로그인 회원 로그인/회원가입", description = "회원의 이메일, 닉네임 정보로 로그인/회원가입을 진행한다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "로그인/회원가입 성공")})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SocialLogin { }
}
