package Movie.MovieCommunity.web.apiDto.board;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardAPIRequest {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long movieId;
}
