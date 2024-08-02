package teamkiim.koffeechat.domain.post.dev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostListResponse;
import teamkiim.koffeechat.domain.post.community.dto.response.CommunityPostResponse;
import teamkiim.koffeechat.domain.post.dev.dto.response.DevPostListResponse;
import teamkiim.koffeechat.domain.post.dev.dto.response.DevPostResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "개발 게시글 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DevPostApiDocument {


    /**
     * 개발 게시글 최초 임시 저장
     */
    @Operation(summary = "게시글 최초 임시 저장", description = "사용자가 개발 게시판에 글을 작성할 때, 최초 임시 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성된 게시글의 PK를 반환한다.",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 게시글을 쓰려고 하는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "사용자를 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface InitPostApiDoc {
    }

    /**
     * 개발 게시글 작성 취소
     */
    @Operation(summary = "게시글 작성 취소", description = "사용자가 게시물을 작성하다가 취소하면 관련 도메인을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 작성 취소 완료"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "postId에 해당하는 게시물이 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CancelPostApiDoc {
    }

    /**
     * 개발 게시글 작성
     */
    @Operation(summary = "게시글 저장", description = "사용자가 개발 게시판에 게시글을 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성된 게시글을 반환한다.",
                    content = @Content(schema = @Schema(implementation = DevPostResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 게시글을 쓰려고 하는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "제목 없이 게시글을 쓰려고 하는 경우",
                            value = "{\"code\":400, \"message\":\"제목을 입력해 주세요.\"}"),
                            @ExampleObject(name = "본문 없이 게시글을 쓰려고 하는 경우",
                                    value = "{\"code\":400, \"message\":\"내용을 입력해 주세요.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SavePostApiDoc {
    }

    /**
     * 개발 게시글 목록 조회
     */
    @Operation(summary = "게시글 목록 조회", description = "사용자가 개발 게시글 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "개발 게시글 리스트를 반환한다. 만약 사진이 없으면 image 관련 필드는 null이 들어간다.",
                    content = @Content(schema = @Schema(implementation = DevPostListResponse.class))),
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShowListApiDoc {
    }

    /**
     * 커뮤니티 게시글 상세 조회
     */
    @Operation(summary = "게시글 상세 조회", description = "사용자가 개발 게시글 단건을 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "개발 게시글을 반환한다.",
                    content = @Content(schema = @Schema(implementation = DevPostResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 게시글을 상세 조회하는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "postId에 해당하는 게시글이 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShowPostApiDoc {
    }

    /**
     * 커뮤니티 게시글 수정
     */
    @Operation(summary = "게시글 수정", description = "사용자가 개발 게시글을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "개발 게시글을 반환한다.",
                    content = @Content(schema = @Schema(implementation = DevPostResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "제목 없이 게시글을 쓰려고 하는 경우",
                            value = "{\"code\":400, \"message\":\"제목을 입력해 주세요.\"}"),
                            @ExampleObject(name = "본문 없이 게시글을 쓰려고 하는 경우",
                                    value = "{\"code\":400, \"message\":\"내용을 입력해 주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "게시글을 수정하려는 회원에 대한 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"현재 로그인된 계정이 존재하지 않습니다.\"}"),
                            @ExampleObject(name = "id에 해당하는 게시글이 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ModifyPostApiDoc {
    }
}
