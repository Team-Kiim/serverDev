package teamkiim.koffeechat.domain.memberfollow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import teamkiim.koffeechat.domain.memberfollow.dto.MemberFollowListResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "회원 팔로우 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberFollowApiDocument {

    /**
     * 팔로우
     */
    @Operation(summary = "회원 팔로우", description = "사용자가 다른 사용자를 팔로우/팔로우 취소한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "다른 사용자의 팔로워 수를 반환한다.",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 구독을 누르는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}

            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface FollowMember {
    }

    /**
     * 본인 follower list 확인
     */
    @Operation(summary = "본인 팔로워 목록 확인", description = "사용자가 본인의 팔로워 목록을 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 본인의 팔로워 리스트를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyFollowerList {
    }

    /**
     * 사용자 follower list 확인
     */
    @Operation(summary = "회원 팔로워 목록 확인", description = "사용자가 특정 회원의 팔로워 목록을 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원의 팔로워 리스트를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface FollowerList {
    }

    /**
     * 본인 following list 확인
     */
    @Operation(summary = "본인 팔로잉 목록 확인", description = "사용자가 본인의 팔로잉 목록을 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 본인의 팔로잉 리스트를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyFollowingList {
    }

    /**
     * 사용자 following list 확인
     */
    @Operation(summary = "회원 팔로잉 목록 확인", description = "사용자가 특정 회원의 팔로잉 목록을 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원의 팔로잉 리스트를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface FollowingList {
    }

    /**
     * 사용자 follower list 검색
     */
    @Operation(summary = "회원 팔로워 목록에서 사용자 검색", description = "사용자가 팔로워 목록에서 검색을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로워 리스트에서 검색 결과를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SearchFollowerList {
    }

    /**
     * 사용자 following list 검색
     */
    @Operation(summary = "회원 팔로잉 목록에서 사용자 검색", description = "사용자가 팔로잉 목록에서 검색을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로잉 리스트에서 검색 결과를 반환한다.",
                    content = @Content(schema = @Schema(implementation = MemberFollowListResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SearchFollowingList {
    }
}
