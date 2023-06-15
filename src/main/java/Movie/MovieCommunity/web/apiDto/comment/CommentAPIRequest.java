package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentAPIRequest{
    @NotNull
    private Long movieId;
    @NotNull
    private String content;
    //private Long parentId;
}