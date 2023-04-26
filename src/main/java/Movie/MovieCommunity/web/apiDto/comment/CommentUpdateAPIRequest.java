package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CommentUpdateAPIRequest extends CommentAPI{

    @NotNull
    private String content;
}
