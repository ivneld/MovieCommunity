package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.web.apiDto.movie.entityDto.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieDetailResponse {
    private Long id;
    private int tmId;
    private String posterPath;
    private int interest;
    private boolean myInterest;
    private String movieNm;
    private Integer openDt;
    private float voteAverage;
    private String watchGradeNm;
    //private String prdtStatNm;
    private Integer showTm;
    private String nationNm;
    private Long audiAcc;
    private List<String> genres = new ArrayList<>();
    private List<String> company = new ArrayList<>();
    private String overview;
    private List<String> ott = new ArrayList<>();

    private List<CreditDto> credits = new ArrayList<>();
    private List<MonthWeekDto> monthWeeks = new ArrayList<>();

    private List<ShortCommentDto> shortComments = new ArrayList<>();
//    private String directorNm;
    private List<BoardDto> boards = new ArrayList<>();

    private List<SeriesDto> series = new ArrayList<>();
    private String videoUrl;

    @Builder
    public MovieDetailResponse(Long id, int tmId, String posterPath, int interest,boolean myInterest, String movieNm, Integer openDt, float voteAverage, String watchGradeNm, Integer showTm, String nationNm, Long audiAcc, String overview, String videoUrl) {
        this.id = id;
        this.tmId = tmId;
        this.posterPath = posterPath;
        this.interest = interest;
        this.myInterest = myInterest;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.voteAverage = voteAverage;
        this.watchGradeNm = watchGradeNm;
        this.showTm = showTm;
        this.nationNm = nationNm;
        this.audiAcc = audiAcc;
        this.overview = overview;
        this.videoUrl = videoUrl;
    }
    public void addCompany(String company){
        this.company.add(company);
    }
    public void addGenre(String genre){
        this.genres.add(genre);
    }
    public void addCredit(CreditDto creditDto){
        this.credits.add(creditDto);
    }
}
