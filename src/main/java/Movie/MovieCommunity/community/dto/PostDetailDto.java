package Movie.MovieCommunity.community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@NoArgsConstructor
public class PostDetailDto {
    private List<CommentDto.Response> comments;
    private UserDto.Response user;
    private boolean IsPostWriter;
    private List<Integer> IsCommentWriter;
    private PostsDto.Response post;

}
