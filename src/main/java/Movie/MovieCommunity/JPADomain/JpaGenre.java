package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaGenre {
    public JpaGenre(String genreNm) {
        this.genreNm = genreNm;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;
    private String genreNm;
}
