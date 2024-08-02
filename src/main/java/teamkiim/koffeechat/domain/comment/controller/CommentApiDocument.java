package teamkiim.koffeechat.domain.comment.controller;

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

@Tag(name = "댓글 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentApiDocument {

    /**
     * 댓글 작성
     */
    @Operation(summary = "댓글 저장", description = "사용자가 게시물에 댓글을 작성한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "사용자가 작성한 댓글 저장 완료"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "사용자를 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다.\"}"),
                            @ExampleObject(name = "postId에 해당하는 게시글이 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}")
                    })
            )
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SaveCommentApiDoc {}

    /**
     * 댓글 수정
     */
    @Operation(summary = "댓글 수정", description = "사용자가 자신이 작성한 댓글을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "사용자가 수정한 댓글 저장 완료"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "해당 댓글을 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 댓글이 존재하지 않습니다.\"}")
                    })
            )
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ModifyCommentApiDoc {}

    /**
     * 댓글 삭제
     */
    @Operation(summary = "댓글 삭제", description = "사용자가 자신이 작성한 댓글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자가 삭제한 댓글 삭제 완료"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "해당 댓글을 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 댓글이 존재하지 않습니다.\"}")
                    })
            )
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DeleteCommentApiDoc {}
}
