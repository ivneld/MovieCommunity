package Movie.MovieCommunity.web.apiDto.board;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@NotNull
public class BoardLikeAPIRequest {
    private Long memberId;
    private Long boardId;
}
