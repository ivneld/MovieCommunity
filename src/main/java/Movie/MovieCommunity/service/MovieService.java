package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.dataCollection.MovieDataService;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.CreditDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.SeriesDto;
import Movie.MovieCommunity.web.apiDto.movie.response.MovieDetailResponse;
import Movie.MovieCommunity.web.apiDto.movie.response.YearRankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final MovieDataService movieDataService;
    public List<YearRankingResponse> yearRanking(int openDt, Long memberId){
        Member member;
        if(memberId != null){
            member = memberRepository.findById(memberId).orElseThrow();
        } else {
            member = null;
        }
        int startDt = openDt*10000;
        int endDt = (openDt+1)*10000;
        List<Movie> yearRankingByOpenDt = movieRepository.findYearRankingByOpenDt(startDt, endDt);
        List<YearRankingResponse> response = new ArrayList<>();
        String url = null;
        int i = 1;
        for (Movie m : yearRankingByOpenDt) {
            long count = m.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();

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
                    .myInterest(count > 0 ? true : false)
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
        return response;
    }
    @Transactional
    public MovieDetailResponse movieDetail(Long movieId, Long memberId){
        try {Member member;
            if(memberId != null){
                member = memberRepository.findById(memberId).orElseThrow();
            } else {
                member = null;
            }

            Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("영화가 없습니다."));
            long count = movie.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();
            MovieDetailResponse movieDetailResponse = MovieDetailResponse.builder()
                    .id(movie.getId())
                    .tmId(movie.getTmId())
                    .posterPath(movie.getPosterPath())
                    .interest(movie.getLikeMovies().size())
                    .myInterest(count > 0 ? true : false)
                    .movieNm(movie.getMovieNm())
                    .openDt(movie.getOpenDt())
                    .voteAverage(movie.getVoteAverage())
                    .watchGradeNm(movie.getWatchGradeNm())
                    .showTm(movie.getShowTm())
                    .nationNm(movie.getNationNm())
                    .audiAcc(movie.getAudiAcc())
                    .overview(movie.getOverview())
                    .build();
            if (movie.getVideos().size() != 0){movieDetailResponse.setVideoUrl(movie.getVideos().get(0).getUrl());}
            for (JpaMovieWithCompany mc : movie.getMovieWithCompanies()) {
                if(mc.getCompany().getCompanyPartNm().equals("제작사")){
                    movieDetailResponse.addCompany(mc.getCompany().getCompanyNm());
                }
            }
            for (JpaMovieWithGenre mg : movie.getMovieWithGenres()) {
                movieDetailResponse.addGenre(mg.getGenre().getGenreNm());
            }
            for (MovieWithCredit mc : movie.getMovieWithCredits()) {
                Credit credit = mc.getCredit();
                movieDetailResponse.addCredit(new CreditDto(credit.getId(), credit.getActorNm(), credit.getProfile_path(), credit.getCreditCategory()));
            }
            List<SeriesDto> seriesDtos = movieDataService.selectSeries(movie.getCollectionId());
            for (SeriesDto seriesDto : seriesDtos) {
                movieDataService.collectByTmId(seriesDto.getId());
            }
            movieDetailResponse.setSeries(seriesDtos);
            return movieDetailResponse;
        }catch(EntityNotFoundException e){

        }

        return null;
    }

}
