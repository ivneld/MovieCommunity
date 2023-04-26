package Movie.MovieCommunity.JPADomain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LikeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_movie_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="board_id")
    private Board board;
    @Builder
    public LikeBoard(Member member, Board board) {
        this.member = member;
        this.board = board;
    }
}
