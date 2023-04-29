package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "genre")
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
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'default_value'")
    private String genreNm;
}
