package Movie.MovieCommunity.community.dto;

import Movie.MovieCommunity.community.response.SubCommentResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

@Data
@Setter
@NoArgsConstructor
public class PostDetailDto {
    private UserDto.Response user;
    private Boolean IsPostWriter;     // 게시글 작성자인지
    private Boolean IsHeartWriter;    // 게시글에 하트를 눌렀는지
    private List<Integer> IsCommentWriter;    // 댓글 작성자인지
    private List<Integer> IsCommentHeartWriter;   // 댓글에 하트를 눌렀는지
    private List<JSONArray> IsSubCommentWriter;   // 대댓글 작성자인지
    private List<JSONArray> IsSubCommentHeartWriter; // 대댓글에 하트를 눌렀는지
    private PostsDto.Response post;
}
