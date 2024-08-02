package teamkiim.koffeechat.domain.corp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "[ADMIN] 현직자 인증 도메인 관리 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CorpAdminApiDocument {

    /**
     * 도메인 등록
     */
    @Operation(summary = "현직자 인증 도메인 추가", description = "검증된 회사 도메인을 추가한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도메인 저장 완료"),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "관리자 이외의 접근인 경우",
                            value = "{\"code\": 403, \"message\": \"관리자만 접근이 가능합니다.\"}")}
            )),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "이미 존재하는 회사 도메인인 경우",
                            value = "{\"code\": 409, \"message\": \"이미 존재하는 회사 도메인입니다.\"}")}
            )),
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface EnrollCorpDomain {
    }

    /**
     * 도메인 상태 관리 : 승인, 거절
     */
    @Operation(summary = "현직자 인증 도메인 상태 관리", description = "사용자가 검증 요청한 도메인에 대해 [승인/ 거절] 상태 관리 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도메인 승인/거절 완료"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "도메인 상태 요청이 올바르지 않은 경우",
                            value = "{\"code\": 400, \"message\": \"도메인 상태 요청은 WAITING, APPROVED, REJECTED 중에 선택해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "관리자 이외의 접근인 경우",
                            value = "{\"code\": 403, \"message\": \"관리자만 접근이 가능합니다.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "회사 도메인 저장 정보를 찾을 수 없는 경우",
                            value = "{\"code\": 404, \"message\": \"해당 회사 도메인 정보를 찾을 수 없습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ManageCorpDomain {
    }

    /**
     * 도메인 삭제
     */
    @Operation(summary = "현직자 인증 도메인 삭제", description = "회사 도메인 관리, 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도메인 삭제 완료"),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "관리자 이외의 접근인 경우",
                            value = "{\"code\": 403, \"message\": \"관리자만 접근이 가능합니다.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "회사 도메인 저장 정보를 찾을 수 없는 경우",
                            value = "{\"code\": 404, \"message\": \"해당 회사 도메인 정보를 찾을 수 없습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DeleteCorpDomain {
    }

    /**
     * 도메인 목록 출력
     */
    @Operation(summary = "현직자 인증 도메인 리스트 확인", description = "등록되어있는 회사 도메인 리스트를 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리스트 출력 완료"),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "관리자 이외의 접근인 경우",
                            value = "{\"code\": 403, \"message\": \"관리자만 접근이 가능합니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    }

    /**
     * 도메인 키워드 기반 검색
     */
    @Operation(summary = "현직자 인증 도메인을 키워드 기반 검색한다.", description = "회사 이름, 도메인 기준 검색")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 완료"),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "관리자 이외의 접근인 경우",
                            value = "{\"code\": 403, \"message\": \"관리자만 접근이 가능합니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Search {
    }
}
