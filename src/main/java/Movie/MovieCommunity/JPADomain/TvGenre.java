package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name="tv_genre")
@SequenceGenerator(
        name="GENRE_SEQ_GENERATOR",
        sequenceName="GENRE_SEQ",
        initialValue=1
)
@Entity
public class TvGenre {

    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE
            ,generator="GENRE_SEQ_GENERATOR"
    )
    private Long id;

    @Column(name = "genre_tm_id")
    private Long genreTmId;

    @Column(name = "genre_name")
    private String genreNm;



    public TvGenre(Long genreTmId, String genreNm) {
        this.genreTmId = genreTmId;
        this.genreNm = genreNm;
    }

    protected TvGenre() {

    }
}
