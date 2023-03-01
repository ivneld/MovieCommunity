package Movie.MovieCommunity.web.form;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {
    private Long id;
    @NotEmpty(message = "필수 값입니다.")
    private String content;

    private Member member;

    private Board board;

    public CommentForm( String content, Member member, Board board) {

        this.content = content;
        this.member = member;
        this.board = board;
    }
}
