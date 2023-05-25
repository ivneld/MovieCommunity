package Movie.MovieCommunity.web.form;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardForm {
    private Long id;
    @NotEmpty(message = "필수 값 입니다.")
    private String title;
    @NotEmpty(message = "필수 값 입니다.")
    private String content;
    private Member member;
    private Movie movie;

    public BoardForm() {
    }

    public BoardForm(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.member = board.getMember();
        this.movie = board.getMovie();
    }
}
