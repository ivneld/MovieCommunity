package Movie.MovieCommunity.JPADomain;

<<<<<<< HEAD
=======

>>>>>>> 84e9f5fe60ac96a6fccd7556886de3e6e2489a12
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name="tv")
@Entity(name = "tv")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tv {
    /**
     "logo_path":"/t2yyOv40HZeVlLjYsCsPHnWLk4W.jpg","provider_name":"Netflix","provider_id":8
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_id")
    private Long tvId;

    @Column(name = "tm_id")
    private Long tmId;

    @Column(name = "tv_name",columnDefinition = "TEXT")
    private String tvNm;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "origin_language")
    private String originLanguage;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private Double popularity;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "back_drop_path")
    private String backDropPath;

    @Column(name = "first_air_date")
    private String firstAirDate;

    @OneToMany(mappedBy = "tv",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TvWithGenre> tvWithGenres = new ArrayList<>();

    @OneToMany(mappedBy = "tv",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TvWithProvider> tvWithProviders = new ArrayList<>();


//    public void addProvider(TvProvider provider) {
//        providers.add(provider);
//    }

//    public void addGenre(TvGenre genre) {
//        genres.add(genre);
//    }

//    public Tv(String tvNm, String originCountry, String originLanguage, String overview, Double popularity, String posterPath, String backDropPath, String firstAirDate) {
//        this.tvNm = tvNm;
//        this.originCountry = originCountry;
//        this.originLanguage = originLanguage;
//        this.overview = overview;
//        this.popularity = popularity;
//        this.posterPath = posterPath;
//        this.backDropPath=backDropPath;
//        this.firstAirDate = firstAirDate;
//    }


    public Tv( Long tmId, String tvNm, String originCountry, String originLanguage, String overview, Double popularity, String posterPath, String backDropPath, String firstAirDate) {
        this.tmId = tmId;
        this.tvNm = tvNm;
        this.originCountry = originCountry;
        this.originLanguage = originLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.firstAirDate = firstAirDate;

    }
}

/**{"genres":[{"id":10759,"name":"Action & Adventure"},{"id":16,"name":"애니메이션"},{"id":35,"name":"코미디"},{"id":80,"name":"범죄"},{"id":99,"name":"다큐멘터리"},{"id":18,"name":"드라마"},{"id":10751,"name":"가족"},{"id":10762,"name":"Kids"},{"id":9648,"name":"미스터리"},{"id":10763,"name":"News"},{"id":10764,"name":"Reality"},{"id":10765,"name":"Sci-Fi & Fantasy"},{"id":10766,"name":"Soap"},{"id":10767,"name":"Talk"},{"id":10768,"name":"War & Politics"},{"id":37,"name":"서부"}]}*/