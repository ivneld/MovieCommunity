package Movie.MovieCommunity.JPADomain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="like_movie")@Table(name = "like_movie")
@Getter
@NoArgsConstructor
public class LikeMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_movie_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;
    @Builder
    public LikeMovie(Member member, Movie movie) {
        this.member = member;
        this.movie = movie;
    }
}
