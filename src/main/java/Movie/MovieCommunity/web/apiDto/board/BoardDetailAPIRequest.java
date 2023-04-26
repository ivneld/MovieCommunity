package Movie.MovieCommunity.web.apiDto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@NotNull
public class BoardDetailAPIRequest {
    private Long boarId;
    private String email;
    private String title;
    private String content;
}
