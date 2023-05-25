package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.web.apiDto.movie.response.YearRankingResponse;
import Movie.MovieCommunity.JPARepository.WeeklyBoxOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final WeeklyBoxOfficeRepository weeklyBoxOfficeRepository;

    public List<YearRankingResponse> yearRanking(int openDt){
        int startDt = openDt*10000;
        int endDt = (openDt+1)*10000;
        List<JpaMovie> yearRankingByOpenDt = movieRepository.findYearRankingByOpenDt(startDt, endDt);
        List<YearRankingResponse> response = new ArrayList<>();
        String url = null;
        int i = 1;
        for (JpaMovie m : yearRankingByOpenDt) {

            int topCnt = 0;
            YearRankingResponse yearRankingResponse = YearRankingResponse.builder()
                    .rank(i)
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
            if (i==1 && m.getVideos() != null) {
                url = m.getVideos().get(0).getUrl();
                yearRankingResponse.setUrl(url);
            }
            for (Comment comment : m.getComments()) {
                if(topCnt < comment.getLikeComments().size()){
                    topCnt = comment.getLikeComments().size();
                    yearRankingResponse.setTopComment(comment.getContent());
                }
            }
            i++;
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

    /**
     * @param year -> 주간별로 분할할 년도
     * @param week -> 원하는 주차
     * @return -> YearRankingResponse List
     * (weeklyBoxOffice, movie 에서 존재하는 영화는 다를 수 있다.)
     */
    public List<YearRankingResponse> weeklyRanking(int year, int week) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        TemporalField weekOfYear = WeekFields.of(Locale.KOREA).weekOfYear();

        int startWeek = startDate.get(weekOfYear);
        int targetWeek = startWeek + week - 1;

        LocalDate firstDayOfWeek = startDate.with(weekOfYear, targetWeek);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findByOpenDtBetween(firstDayOfWeek, lastDayOfWeek);

        List<YearRankingResponse> result = new ArrayList<>();

        for (JpaWeeklyBoxOffice weeklyBoxOffice : weeklyBoxOffices) {
            if (movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
                JpaMovie movie = movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).get();

                result.add(YearRankingResponse.builder()
                        .id(movie.getId())
                        .movieNm(movie.getMovieNm())
                        .showTm(movie.getShowTm())
                        .openDt(movie.getOpenDt())
                        .prdtStatNm(movie.getPrdtStatNm())
                        .watchGradeNm(movie.getWatchGradeNm())
                        .overview(movie.getOverview())
                        .posterPath(movie.getPosterPath())
                        .voteAverage(movie.getVoteAverage())
                        .build());
            }
        }
        return result;
    }
}
