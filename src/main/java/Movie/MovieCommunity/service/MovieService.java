package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.web.apiDto.movie.response.YearRankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<YearRankingResponse> yearRanking(int openDt){
        int startDt = openDt*10000;
        int endDt = (openDt+1)*10000;
        List<JpaMovie> yearRankingByOpenDt = movieRepository.findYearRankingByOpenDt(startDt, endDt);
        List<YearRankingResponse> response = new ArrayList<>();
        int i = 1;
        for (JpaMovie m : yearRankingByOpenDt) {
            int topCnt = 0;
            YearRankingResponse yearRankingResponse = YearRankingResponse.builder()
                    .rank(i++)
                    .id(m.getId())
                    .movieNm(m.getMovieNm())
                    .showTm(m.getShowTm())
                    .openDt(m.getOpenDt())
                    .prdtStatNm(m.getPrdtStatNm())
                    .voteAverage(m.getVoteAverage())
                    .watchGradeNm(m.getWatchGradeNm())
                    .overview(m.getOverview().length() > 80 ? m.getOverview().substring(0, 80)+"..." : m.getOverview())
                    .posterPath(m.getPosterPath())
                    .interest(m.getLikeMovies().size())
                    .build();
            for (Comment comment : m.getComments()) {
                if(topCnt < comment.getLikeComments().size()){
                    topCnt = comment.getLikeComments().size();
                    yearRankingResponse.setTopComment(comment.getContent());
                }
            }
            response.add(yearRankingResponse);
        }
//        List<YearRankingResponse> response = yearRankingByOpenDt.stream().map(m -> YearRankingResponse.builder()
//                .id(m.getId())
//                .movieCd(m.getMovieCd())
//                .movieNm(m.getMovieNm())
//                .showTm(m.getShowTm())
//                .openDt(m.getOpenDt())
//                .prdtStatNm(m.getPrdtStatNm())
//                .typeNm(m.getTypeNm())
//                .nationNm(m.getNationNm())
//                .directorNm(m.getDirectorNm())
//                .auditNo(m.getAuditNo())
//                .watchGradeNm(m.getWatchGradeNm())
//                .topScore(m.getTopScore())
//                .salesAcc(m.getSalesAcc())
//                .audiAcc(m.getAudiAcc())
//                .tmId(m.getTmId())
//                .overview(m.getOverview())
//                .backdropPath(m.getBackdropPath())
//                .posterPath(m.getPosterPath())
//                .popularity(m.getPopularity())
//                .voteAverage(m.getVoteAverage())
//                .voteCount(m.getVoteCount())
//                .collectionId(m.getCollectionId())
//                .seriesName(m.getSeriesName())
//                .collectionBackdropPath(m.getCollectionBackdropPath())
//                .collectionPosterPath(m.getCollectionPosterPath())
//                //.topComment(m.getComments())
//                .build()
//        ).collect(Collectors.toList());
        return response;
    }

}
