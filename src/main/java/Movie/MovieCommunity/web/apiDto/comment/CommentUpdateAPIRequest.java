package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentUpdateAPIRequest{
    @NotNull
    private Long commentId;
    @NotNull
    private String content;
}
