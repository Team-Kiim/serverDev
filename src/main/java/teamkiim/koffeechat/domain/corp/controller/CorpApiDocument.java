package teamkiim.koffeechat.domain.corp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import teamkiim.koffeechat.domain.corp.dto.response.CorpDomainResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "현직자 인증 도메인 관리 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CorpApiDocument {

    /**
     * 도메인 승인 요청
     */
    @Operation(summary = "현직자 인증 도메인 검증 요청", description = "현직자 인증 시 회사 도메인 검증 요청을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도메인 검증 요청 저장 완료"),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 요청한 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "승인 거절된 도메인으로 도메인 등록 요청한 경우",
                            value = "{\"code\":403, \"message\":\"승인 거절된 도메인입니다. 이메일 확인 후 문의해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "이미 승인된 도메인에 대해 도메인 등록 요청한 경우",
                            value = "{\"code\":409, \"message\":\"이미 존재하는 도메인입니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CreateWaitingCorp {
    }

    /**
     * 회사 도메인 검색
     */
    @Operation(summary = "회사 도메인 검색", description = "현직자 인증 시 회사 이름/도메인을 통해 검색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색된 회사 도메인을 반환한다.",
                    content = @Content(schema = @Schema(implementation = CorpDomainResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 요청한 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface FindCorpDomain {
    }

    /**
     * 현직자 인증 시 이메일 전송
     */
    @Operation(summary = "현직자 인증 시 이메일 인증 메세지 전송", description = "현직자 인증 시 이메일 인증 메세지를 전송한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 전송 완료"),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 요청한 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "승인 거절된 도메인으로 이메일 검증 코드를 요청한 경우",
                            value = "{\"code\":403, \"message\":\"승인 거절된 도메인입니다. 이메일 확인 후 문의해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "등록되어있지 않은 도메인에 대해 이메일 검증 코드를 요청한 경우",
                            value = "{\"code\":409, \"message\":\"등록 요청이 되어있는 도메인입니다. 도메인 승인이 되면 다시 인증해주세요.\"}"),
                            @ExampleObject(name = "승인 대기중인 도메인에 대해 이메일 검증 코드를 요청한 경우",
                                    value = "{\"code\":409, \"message\":\"등록 요청이 되어있는 도메인입니다. 도메인 승인이 되면 다시 인증해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "이메일이 정상적으로 전송되지 않은 경우",
                            value = "{\"code\": 500, \"message\": \"이메일 전송에 실패했습니다. 이메일이 올바른지 확인해주세요.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CreateEmailAuth {
    }

    /**
     * 회사 이메일 인증 코드 확인
     */
    @Operation(summary = "현직자 인증 시 이메일로 전송한 코드 확인", description = "현직자 인증 시 이메일로 전송한 코드를 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 코드 확인 완료"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "인증 코드가 불일치하는 경우",
                            value = "{\"code\": 400, \"message\": \"인증 코드가 일치하지 않습니다.\"}")}
            )),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 요청한 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CheckEmailAuthCode {
    }
}
