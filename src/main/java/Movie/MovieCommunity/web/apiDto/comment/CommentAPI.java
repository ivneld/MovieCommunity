package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public abstract class CommentAPI {
    @NotNull
    private Long commentId;
    @NotNull
    private Long memberId;
}
