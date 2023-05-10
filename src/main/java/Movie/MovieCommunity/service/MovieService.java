package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.web.apiDto.movie.YearRankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<YearRankingResponse> yearRanking(int openDt){
        int startDt = openDt*10000;
        int endDt = (openDt+1)*10000;
        List<JpaMovie> yearRankingByOpenDt = movieRepository.findYearRankingByOpenDt(startDt, endDt);
        List<YearRankingResponse> response = yearRankingByOpenDt.stream().map(m -> YearRankingResponse.builder()
                .id(m.getId())
                .movieCd(m.getMovieCd())
                .movieNm(m.getMovieNm())
                .showTm(m.getShowTm())
                .openDt(m.getOpenDt())
                .prdtStatNm(m.getPrdtStatNm())
                .typeNm(m.getTypeNm())
                .nationNm(m.getNationNm())
                .directorNm(m.getDirectorNm())
                .auditNo(m.getAuditNo())
                .watchGradeNm(m.getWatchGradeNm())
                .topScore(m.getTopScore())
                .salesAcc(m.getSalesAcc())
                .audiAcc(m.getAudiAcc())
                .tmId(m.getTmId())
                .overview(m.getOverview())
                .backdropPath(m.getBackdropPath())
                .posterPath(m.getPosterPath())
                .popularity(m.getPopularity())
                .voteAverage(m.getVoteAverage())
                .voteCount(m.getVoteCount())
                .collectionId(m.getCollectionId())
                .seriesName(m.getSeriesName())
                .collectionBackdropPath(m.getCollectionBackdropPath())
                .collectionPosterPath(m.getCollectionPosterPath())
                .build()
        ).collect(Collectors.toList());
        return response;
    }

}
