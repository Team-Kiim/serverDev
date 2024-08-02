package teamkiim.koffeechat.domain.post.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import teamkiim.koffeechat.domain.post.common.dto.response.BookmarkPostListResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "게시글 공통 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostApiDocument {

    /**
     * 좋아요
     */
    @Operation(summary = "게시글 좋아요", description = "사용자가 개발, 커뮤니티 게시판에 좋아요를 누른다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 좋아요 수를 반환한다.",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 좋아요를 누르는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "게시물을 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 게시물이 존재하지 않습니다\"}")}

            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface LikeApiDoc {
    }

    /**
     * 북마크
     */
    @Operation(summary = "게시글 북마크", description = "사용자가 개발, 커뮤니티 게시판에 북마크를 누른다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시물 북마크 수를 반환한다.",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 북마크를 누르는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "게시물을 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 게시물이 존재하지 않습니다\"}")}

            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface BookmarkApiDoc {
    }

    /**
     * 북마크 리스트 조회
     */
    @Operation(summary = "사용자 북마크 게시물 리스트 조회", description = "로그인한 사용자의 북마크 게시물 리스트롤 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크한 게시물 리스트를 반환한다.",
                    content = @Content(schema = @Schema(implementation = BookmarkPostListResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 북마크 리스트를 조회하려고 하는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface BookmarkedPostListApiDoc {
    }

    /**
     * 게시글 삭제 (soft delete)
     */
    @Operation(summary = "게시글 삭제", description = "게시글 작성자가 본인이 작성한 게시물을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "삭제된 게시글의 pk를 반환한다.",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "게시물을 찾을 수 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 게시물이 존재하지 않습니다\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DeletePostApiDoc {
    }

}
