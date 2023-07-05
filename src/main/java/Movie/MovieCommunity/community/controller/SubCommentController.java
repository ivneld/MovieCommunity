package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.community.dto.CommentDto;
import Movie.MovieCommunity.community.dto.SubCommentRequestDto;
import Movie.MovieCommunity.community.dto.UserDto;
import Movie.MovieCommunity.community.response.ResponseDto;
import Movie.MovieCommunity.community.service.SubCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Builder
@Validated
@RequiredArgsConstructor
@RestController
@Tag(name="community subComment", description = "커뮤니티 대댓글 API")
public class SubCommentController {

    private final SubCommentService subCommentService;


    // 생성 / 로그인 필요 // auth 추가
    @Operation(method = "post", summary = "커뮤니티 대댓글 생성")
    @RequestMapping(value = "/api/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createReComment(@RequestBody SubCommentRequestDto requestDto,
                                          HttpServletRequest request) {
        return subCommentService.createReComment(requestDto, request);
    }

    //대댓글 조회
    @Operation(method = "get", summary = "커뮤니티 대댓글 조회")
    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long id){
        return subCommentService.getAllSubcommentsByComment(id);
    }

    // 수정 / 로그인 필요 ( 여기서 id는 수정하고 싶은 대댓글 id ) auth 추가
    @Operation(method = "put", summary = "커뮤니티 대댓글 수정")
    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubComment(@PathVariable Long id, @RequestBody SubCommentRequestDto RequestDto,
                                           HttpServletRequest request) {
        return subCommentService.updateSubComment(id, RequestDto, request);
    }

    // 삭제 / 로그인 필요
    @Operation(method = "delete", summary = "커뮤니티 대댓글 삭제")
    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return subCommentService.deleteSubComment(id, request);
    }
}