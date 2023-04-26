package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
public abstract class CommentAPI {
    @NotNull
    private Long commentId;
    @NotNull
    private Long memberId;
}
