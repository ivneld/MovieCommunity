package Movie.MovieCommunity.web.dto;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;


@Data
public class BoardDto {
    private Long id;
    private String title;
    private int like;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.like = board.getLikeCnt();
        this.memberName = board.getMember().getName();
        this.movieNm = board.getMovie().getMovieNm();
    }

    private String memberName;
    private String movieNm;
}
