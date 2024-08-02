package teamkiim.koffeechat.domain.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import teamkiim.koffeechat.domain.file.dto.response.ImagePathResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(name = "파일 API")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileApiDocument {

    /**
     * 이미지 단건 저장
     */
    @Operation(summary = "이미지 파일 단건 저장", description = "이미지 파일을 단건 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성된 이미지 파일의 정보를 반환한다.",
                    content = @Content(schema = @Schema(implementation = ImagePathResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "postId에 해당하는 게시글이 없는 경우",
                            value = "{\"code\":404, \"message\":\"해당 게시글이 존재하지 않습니다.\"}")}
            )),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "FILE I/O 에러가 난 경우",
                            value = "{\"code\":500, \"message\":\"파일 저장에 실패했습니다.\"}")}
            )),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "파일 디렉토리가 존재하지 않거나, 파일 형식이 잘못된 경우",
                            value = "{\"code\":400, \"message\":\"파일 요청이 올바르지 않습니다.\"}")}
            ))
    })
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SaveImageFile {}

}
