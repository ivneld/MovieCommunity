package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class CommentAPIRequest{
    @NotNull
    private Long movieId;
    @Column(nullable = false, length = 100)
    private String content;
    //private Long parentId;
}