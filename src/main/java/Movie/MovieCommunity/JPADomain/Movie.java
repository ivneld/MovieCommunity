package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import Movie.MovieCommunity.JPADomain.dto.TmdbResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
movieCd	문자열	영화코드를 출력합니다.
movieNm	문자열	영화명(국문)을 출력합니다.
movieNmEn	문자열	영화명(영문)을 출력합니다.
movieNmOg	문자열	영화명(원문)을 출력합니다.
prdtYear	문자열	제작연도를 출력합니다.
showTm	문자열	상영시간을 출력합니다.
openDt	문자열	개봉연도를 출력합니다.
prdtStatNm	문자열	제작상태명을 출력합니다.
typeNm	문자열	영화유형명을 출력합니다.
nations	문자열	제작국가를 나타냅니다.
nationNm	문자열	제작국가명을 출력합니다.
genreNm	문자열	장르명을 출력합니다.
directors	문자열	감독을 나타냅니다.
peopleNm	문자열	감독명을 출력합니다.
peopleNmEn	문자열	감독명(영문)을 출력합니다.
actors	문자열	배우를 나타냅니다.
peopleNm	문자열	배우명을 출력합니다.
peopleNmEn	문자열	배우명(영문)을 출력합니다.
cast	문자열	배역명을 출력합니다.
castEn	문자열	배역명(영문)을 출력합니다.
showTypes	문자열	상영형태 구분을 나타냅니다.
showTypeGroupNm	문자열	상영형태 구분을 출력합니다.
showTypeNm	문자열	상영형태명을 출력합니다.
audits	문자열	심의정보를 나타냅니다.
auditNo	문자열	심의번호를 출력합니다.
watchGradeNm	문자열	관람등급 명칭을 출력합니다.
companys	문자열	참여 영화사를 나타냅니다.
companyCd	문자열	참여 영화사 코드를 출력합니다.
companyNm	문자열	참여 영화사명을 출력합니다.
companyNmEn	문자열	참여 영화사명(영문)을 출력합니다.
companyPartNm	문자열	참여 영화사 분야명을 출력합니다.
staffs	문자열	스텝을 나타냅니다.
peopleNm	문자열	스텝명을 출력합니다.
peopleNmEn	문자열	스텝명(영문)을 출력합니다.
staffRoleNm	문자열	스텝역할명을 출력합니다.
 */
@Getter
@Table(name="movie")
@Entity(name = "movie")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
//    @Column(nullable = false)
    private String movieCd;
//    @Column(nullable = false)
    private String movieNm;

    public Movie(String movieNm) {
        this.movieNm = movieNm;
    }

    @Builder
    public Movie(  String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String typeNm, String nationNm, String directorNm, String auditNo, String watchGradeNm) {
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.prdtStatNm = prdtStatNm;
        this.typeNm = typeNm;
        this.nationNm = nationNm;
        this.directorNm = directorNm;
        this.auditNo = auditNo;
        this.watchGradeNm = watchGradeNm;
    }

    @Override
    public String toString() {
        return "JpaMovie{" +
                "id=" + id +
                ", movieCd='" + movieCd + '\'' +
                ", movieNm='" + movieNm + '\'' +
                ", showTm=" + showTm +
                ", openDt=" + openDt +
                ", prdtStatNm='" + prdtStatNm + '\'' +
                ", typeNm='" + typeNm + '\'' +
                ", nationNm='" + nationNm + '\'' +
                ", directorNm='" + directorNm + '\'' +
                ", auditNo='" + auditNo + '\'' +
                ", watchGradeNm='" + watchGradeNm + '\'' +
                ", topScore=" + topScore +
                ", salesAcc=" + salesAcc +
                ", audiAcc=" + audiAcc +
                '}';
    }

    //    movieNmEn	문자열	영화명(영문)을 출력합니다.
//    movieNmOg	문자열	영화명(원문)을 출력합니다.
//    prdtYear	문자열	제작연도를 출력합니다.

    private Integer showTm;
    private Integer openDt;
//    @Column(nullable = false)
    private String prdtStatNm;
//    @Column(nullable = false)
    private String typeNm;
    //     nations	문자열	제작국가를 나타냅니다.
    @Column()
    private String nationNm;
    // private Long genreId;
//    private String directors;
    @Column()
    private String directorNm;
    //    peopleNmEn	문자열	감독명(영문)을 출력합니다.
//    actors	문자열	배우를 나타냅니다.
    //private String actorNm;
//    peopleNmEn	문자열	배우명(영문)을 출력합니다.
    //private String cast;//	문자열	배역명을 출력합니다.
//    castEn	문자열	배역명(영문)을 출력합니다.
//    showTypes	문자열	상영형태 구분을 나타냅니다.
//    showTypeGroupNm	문자열	상영형태 구분을 출력합니다.
//    showTypeNm	문자열	상영형태명을 출력합니다.
//    private String audits;// 	문자열	심의정보를 나타냅니다.
    @Column()
    private String auditNo;//	문자열	심의번호를 출력합니다.
    @Column()
    private String watchGradeNm;//	문자열	관람등급 명칭을 출력합니다.

    public Movie(MovieDto movieDto) {
        this.movieCd = movieDto.getMovieCd();
        this.movieNm = movieDto.getMovieNm();
        this.showTm = movieDto.getShowTm();
        this.openDt = movieDto.getOpenDt();
        this.prdtStatNm = movieDto.getPrdtStatNm();
        this.typeNm = movieDto.getTypeNm();
        this.nationNm = movieDto.getNationNm();
        this.auditNo = movieDto.getAuditNo();
        this.watchGradeNm = movieDto.getWatchGradeNm();
    }

    //    companys	문자열	참여 영화사를 나타냅니다.
    //private Long companyId;
//    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.
//    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
//    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
//    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
    private Integer topScore;
    //    staffs	문자열	스텝을 나타냅니다.
//    peopleNm	문자열	스텝명을 출력합니다.
//    peopleNmEn	문자열	스텝명(영문)을 출력합니다.
//    staffRoleNm	문자열	스텝역할명을 출력합니다.
    @NumberFormat(pattern = "###,###")
    private Long salesAcc;

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    /*    private Long audiCnt;
                audiInten	문자열	전일 대비 관객수 증감분을 출력합니다.
                audiChange	문자열	전일 대비 관객수 증감 비율을 출력합니다.*/
    @NumberFormat(pattern = "###,###")
    private Long audiAcc;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieWithCredit> movieWithCredits = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaMovieWithCompany> movieWithCompanies = new ArrayList<>();
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaMovieWithGenre> movieWithGenres = new ArrayList<>();
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeMovie> likeMovies = new ArrayList<>();
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();



    private String releaseDate;


    @OneToMany(mappedBy = "movie")


    public void updateData(MovieDto movieDto) {
        this.nationNm = movieDto.getNationNm();
        this.directorNm = movieDto.getDirectorNm();
        this.auditNo = movieDto.getAuditNo();
        this.watchGradeNm = movieDto.getWatchGradeNm();
        System.out.println("=============================== 업데이트 완료 ================================");
        System.out.println("movieDto = " + movieDto);
        System.out.println("=============================== 업데이트 완료 ================================");

    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public void setSalesAcc(Long salesAcc) {
        this.salesAcc = salesAcc;
    }

    public void setAudiAcc(Long audiAcc) {
        this.audiAcc = audiAcc;
    }

    /**
     * tmdb entity
     */
    private int tmId;
    @Lob
    private String overview;
    @Column()
    private String backdropPath;
    private String posterPath;
    private Float popularity;
    private Float voteAverage;
    private Integer voteCount;
    private Integer collectionId;
    private String seriesName;
    private String collectionBackdropPath;
    private String collectionPosterPath;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();
    public void addTmdbData(TmdbResponseDto tmdbResponseDto) {
        this.tmId = tmdbResponseDto.getId();
        this.overview = tmdbResponseDto.getOverview();
        this.backdropPath = tmdbResponseDto.getBackdropPath();
        this.posterPath = tmdbResponseDto.getPosterPath();
        this.popularity = tmdbResponseDto.getPopularity();
        this.voteAverage = tmdbResponseDto.getVoteAverage();
        this.voteCount = tmdbResponseDto.getVoteCount();
        this.videos = tmdbResponseDto.getVideos();
        this.collectionId = tmdbResponseDto.getCollectionId();
        this.seriesName = tmdbResponseDto.getSeriesName();
        this.collectionBackdropPath = tmdbResponseDto.getCollectionBackdropPath();
        this.collectionPosterPath = tmdbResponseDto.getCollectionPosterPath();
    }

}