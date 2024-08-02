package teamkiim.koffeechat.domain.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "투표 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VoteApiDocument {

    /**
     * 투표
     */
    @Operation(summary = "투표", description = "사용자가 커뮤니티 게시글 투표 항목에 투표한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "투표 완료"),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "로그인하지 않은 사용자가 프로필 사진을 등록하려고 하는 경우",
                            value = "{\"code\":401, \"message\":\"로그인해주세요.\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "사용자를 찾을 수 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 회원이 존재하지 않습니다.\"}"),
                            @ExampleObject(name = "postId에 해당하는 게시글이 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}"),
                            @ExampleObject(name = "post에 투표가 없는 경우",
                                    value = "{\"code\":404, \"message\":\"해당 투표가 존재하지 않습니다.\"}")
                    })
            )
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SaveVoteRecord {
    }
}
