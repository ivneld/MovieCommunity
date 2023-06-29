package Movie.MovieCommunity.community.controller;

import Movie.MovieCommunity.community.dto.HeartRequestDTO;
import Movie.MovieCommunity.community.service.HeartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
@Tag(name="커뮤니티 게시글 좋아요", description = "커뮤니티 게시글 좋아요 API")
public class HeartController {

    private final HeartService heartService;

    @Operation(method = "post", summary = "하트 추가")
    @PostMapping
    public ResponseEntity insert(@RequestBody @Valid HeartRequestDTO heartRequestDTO) throws Exception {
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "put", summary = "하트 삭제")
    @DeleteMapping
    public ResponseEntity delete(@RequestBody @Valid HeartRequestDTO heartRequestDTO) {
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

}