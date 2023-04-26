package Movie.MovieCommunity.web.apiDto.board;

import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.web.dto.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class BoardDetailAPIResponse {
    private BoardDao board;
    private Page<CommentDto> comment;

    public BoardDetailAPIResponse(BoardDao board, Page<CommentDto> comment) {
        this.board = board;
        this.comment = comment;
    }
}
