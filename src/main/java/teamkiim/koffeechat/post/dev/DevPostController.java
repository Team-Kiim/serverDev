package teamkiim.koffeechat.post.dev;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamkiim.koffeechat.request.PostCreateRequestDto;
import teamkiim.koffeechat.response.DevPostViewResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DevPostController {

    private final DevPostService devPostService;

    /**
     * 개발 게시글 생성
     */
    @PostMapping("/dev-write")
    public ResponseEntity<DevPostViewResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postDto) {
        DevPostViewResponseDto devPostDto = devPostService.createDevPost(postDto);

        //생성된 게시물 반환
        return ResponseEntity.ok(devPostDto);
    }

    /**
     * 개발 게시글 목록 조회
     */
    @GetMapping("/dev-posts")
    public ResponseEntity<List<DevPostViewResponseDto>> list() {
        List<DevPostViewResponseDto> posts = devPostService.findDevPosts();  //게시글 목록 불러오기
//
//        //Post entity to dto
//        List<DevPostViewResponseDto> dtoList = posts.stream()
//                .map(post -> {
//                    List<SkillCategory> categories= post.getSkillCategoryList();
//                    List<String> categoryNames= categories.stream()
//                            .map(SkillCategory::getName)
//                            .collect(Collectors.toList());
//                    DevPostViewResponseDto dto = new DevPostViewResponseDto();
//                    dto.set(post, categoryNames);
//                    return dto;
//                })
//                .collect(Collectors.toList());

        return ResponseEntity.ok(posts);
    }

    /**
     * 개발 게시글 제목, 내용, 카테고리 수정
     * 수정 시간도 업데이트됨
     */
    @PostMapping("posts/{postId}/edit")
    public ResponseEntity<DevPostViewResponseDto> updatePost(@PathVariable("postId") Long postId, @RequestBody PostCreateRequestDto postDto) {
        DevPostViewResponseDto dto= devPostService.updatePost(postId, postDto);

        return  ResponseEntity.ok(dto);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId) {
        if (devPostService.deletePost(postId)) {
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
