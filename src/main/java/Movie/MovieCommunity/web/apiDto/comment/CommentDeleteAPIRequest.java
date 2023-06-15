package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentDeleteAPIRequest extends CommentAPI{
    @NotNull
    private Long commentId;
}
