package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentUpdateAPIRequest extends CommentAPI{

    @NotNull
    private String content;
}
