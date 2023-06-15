package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "genre")
@Getter
@Table(name = "genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {
    public Genre(String genreNm) {
        this.genreNm = genreNm;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;
    @Column(nullable = false)
    private String genreNm;


}
