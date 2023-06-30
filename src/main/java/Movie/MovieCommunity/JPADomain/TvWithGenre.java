package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name="tvwithgenre")
@Entity(name = "tvwithgenre")
@NoArgsConstructor
public class TvWithGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_with_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_id")
    private Tv tv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private TvGenre tvGenre;

    public TvWithGenre(Tv tv, TvGenre tvGenre) {
        this.tv = tv;
        this.tvGenre = tvGenre;
    }
}
