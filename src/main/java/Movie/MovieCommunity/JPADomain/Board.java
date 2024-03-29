package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.web.form.BoardForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", like=" + likeCnt +
//                ", member=" + member +
//                ", movie=" + movie +
//                ", comments=" + comments +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    private int likeCnt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private JpaMovie movie;
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public Board updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Board(BoardForm boardForm) {
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.member = boardForm.getMember();
        this.movie = boardForm.getMovie();
    }

}
