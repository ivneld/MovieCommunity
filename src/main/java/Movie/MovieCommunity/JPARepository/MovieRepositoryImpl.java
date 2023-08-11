package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.QJpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.dao.MovieDao;
import Movie.MovieCommunity.JPARepository.dao.MovieWithWeeklyDao;
import Movie.MovieCommunity.JPARepository.dao.QMovieDao;
import Movie.MovieCommunity.JPARepository.dao.QMovieWithWeeklyDao;
import Movie.MovieCommunity.JPARepository.searchCond.MovieSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaWeeklyBoxOffice.*;
import static Movie.MovieCommunity.JPADomain.QMovie.movie;
import static org.thymeleaf.util.StringUtils.isEmpty;

@Repository
public class MovieRepositoryImpl implements QuerydslMovieRepository{
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
//String movieCd, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String typeNm, String nationNm, String directorNm, String watchGradeNm
    @Override
    public Page<MovieDao> findByMovieCond(MovieSearchCond movieSearchCond, Pageable pageable) {
        List<MovieDao> content = jpaQueryFactory.select(new QMovieDao(movie.movieCd, movie.movieNm, movie.showTm, movie.openDt, movie.prdtStatNm, movie.typeNm, movie.nationNm, movie.directorNm, movie.watchGradeNm, movie.topScore))
                .from(movie)
                .where(movieCdEq(movieSearchCond.getMovieNm()),
                        movieNmContain(movieSearchCond.getMovieNm()),
                        movieShowTmGoe(movieSearchCond.getShowTm()),
                        movieOpenDtGoe(movieSearchCond.getOpenDt()),
                        moviePrdtStatNmEq(movieSearchCond.getPrdtStatNm()),
                        movieTypeNmEq(movieSearchCond.getTypeNm()),
                        movieNationNmEq(movieSearchCond.getNationNm()),
                        movieDirectorNmContain(movieSearchCond.getDirectorNm()),
                        movieWatchGradeNmEq(movieSearchCond.getWatchGradeNm()),
                        movieTopScoreGoe(movieSearchCond.getTopScore())
                        )
                .fetch();

        Long count = jpaQueryFactory.select(movie.count())
                .from(movie)
                .where(movieCdEq(movieSearchCond.getMovieNm()),
                        movieNmContain(movieSearchCond.getMovieNm()),
                        movieShowTmGoe(movieSearchCond.getShowTm()),
                        movieOpenDtGoe(movieSearchCond.getOpenDt()),
                        moviePrdtStatNmEq(movieSearchCond.getPrdtStatNm()),
                        movieTypeNmEq(movieSearchCond.getTypeNm()),
                        movieNationNmEq(movieSearchCond.getNationNm()),
                        movieDirectorNmContain(movieSearchCond.getDirectorNm()),
                        movieWatchGradeNmEq(movieSearchCond.getWatchGradeNm())
                )
                .fetchFirst();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public List<MovieWithWeeklyDao> findByShowRange(String showRange) {
        return jpaQueryFactory.select(new QMovieWithWeeklyDao(jpaWeeklyBoxOffice.ranking, movie.id, movie.movieNm, movie.showTm, movie.openDt, movie.prdtStatNm, movie.watchGradeNm, movie.overview, movie.posterPath, movie.voteAverage))
                .from(movie)
                .join(jpaWeeklyBoxOffice)
                .on(movie.movieCd.eq(jpaWeeklyBoxOffice.movieCd))
                .where(jpaWeeklyBoxOffice.showRange.eq(showRange))
                .orderBy(jpaWeeklyBoxOffice.ranking.asc())
                .fetch();
    }


    private BooleanExpression movieCdEq(String movieCd) {
        return isEmpty(movieCd) ? null : movie.movieCd.eq(movieCd);
    }
    private BooleanExpression movieNmContain(String movieNm) {
        return isEmpty(movieNm) ? null : movie.movieNm.contains(movieNm);
    }
    private BooleanExpression movieShowTmGoe(Integer showTm) {
        if(showTm == null)
            return null;
        else {
            return movie.showTm.goe(showTm);
        }
    }
    private BooleanExpression movieOpenDtGoe(Integer openDt) {
        if(openDt == null)
            return null;
        else {
            return movie.openDt.goe(openDt);
        }
    }
    private BooleanExpression moviePrdtStatNmEq(String prdtStatNm) {
        return isEmpty(prdtStatNm) ? null : movie.prdtStatNm.eq(prdtStatNm);
    }
    private BooleanExpression movieTypeNmEq(String typeNm) {
        return isEmpty(typeNm) ? null : movie.typeNm.eq(typeNm);
    }
    private BooleanExpression movieNationNmEq(String nationNm) {
        return isEmpty(nationNm) ? null : movie.nationNm.eq(nationNm);
    }
    private BooleanExpression movieDirectorNmContain(String directorNm) {
        return isEmpty(directorNm) ? null : movie.directorNm.contains(directorNm);
    }
    private BooleanExpression movieWatchGradeNmEq(String watchGradeNm) {
        return isEmpty(watchGradeNm) ? null : movie.watchGradeNm.eq(watchGradeNm);
    }
    private BooleanExpression movieTopScoreGoe(Integer topScore) {
        if(topScore == null)
            return null;
        else {
            return movie.openDt.goe(topScore);
        }
    }
}
