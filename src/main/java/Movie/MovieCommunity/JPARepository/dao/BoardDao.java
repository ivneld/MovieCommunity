package Movie.MovieCommunity.JPARepository.dao;

import Movie.MovieCommunity.JPADomain.Board;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoardDao {
    private Long id;
    private String title;
    private int like;
    private Long movieId;
    private Long memberId;
    private String movieNm;
    private String memberNm;
    @QueryProjection
    public BoardDao(Long id, String title, int like,  Long memberId, String memberNm,Long movieId, String movieNm) {
        this.id = id;
        this.title = title;
        this.like = like;
        this.movieId = movieId;
        this.memberId = memberId;
        this.movieNm = movieNm;
        this.memberNm = memberNm;
    }

    public BoardDao(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.like = board.getLikeCnt();
        this.movieId = board.getMovie().getId();
        this.memberId = board.getMember().getId();
        this.movieNm = board.getMovie().getMovieNm();
        this.memberNm = board.getMember().getName();
    }

    public BoardDao() {
    }
}
