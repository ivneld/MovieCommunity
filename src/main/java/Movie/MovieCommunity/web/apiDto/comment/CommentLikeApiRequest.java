package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentLikeApiRequest {
    @NotNull
    private Long commentId;
    @NotNull
    private Integer like;
}
