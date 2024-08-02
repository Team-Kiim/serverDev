package teamkiim.koffeechat.domain.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import teamkiim.koffeechat.domain.file.service.FileService;
import teamkiim.koffeechat.global.AuthenticatedMemberPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "파일 API")
public class FileController {

    private final FileService fileService;

    /**
     * 이미지 단건 저장
     */
    @AuthenticatedMemberPrincipal
    @PostMapping("/image")
    @FileApiDocument.SaveImageFile
    public ResponseEntity<?> saveImageFile(@RequestPart(value = "file") MultipartFile multipartFile,
                                           @RequestPart(value = "postId") Long postId) {

        return fileService.saveImageFile(multipartFile, postId);
    }

}
