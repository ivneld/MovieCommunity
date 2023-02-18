package Movie.MovieCommunity.web.form;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardForm {
    private Long id;
    @NotEmpty(message = "필수 값 입니다.")
    private String title;
    @NotEmpty(message = "필수 값 입니다.")
    private String content;
    private Member member;
    private JpaMovie movie;

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
