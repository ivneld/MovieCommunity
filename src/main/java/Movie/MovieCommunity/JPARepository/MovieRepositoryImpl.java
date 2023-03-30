package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.QJpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import Movie.MovieCommunity.JPARepository.dao.MovieDao;
import Movie.MovieCommunity.JPARepository.dao.QMovieDao;
import Movie.MovieCommunity.JPARepository.searchCond.MovieSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
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
        List<MovieDao> content = jpaQueryFactory.select(new QMovieDao(jpaMovie.movieCd, jpaMovie.movieNm, jpaMovie.showTm, jpaMovie.openDt, jpaMovie.prdtStatNm, jpaMovie.typeNm, jpaMovie.nationNm, jpaMovie.directorNm, jpaMovie.watchGradeNm, jpaMovie.topScore))
                .from(jpaMovie)
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

        Long count = jpaQueryFactory.select(jpaMovie.count())
                .from(jpaMovie)
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


    private BooleanExpression movieCdEq(String movieCd) {
        return isEmpty(movieCd) ? null : jpaMovie.movieCd.eq(movieCd);
    }
    private BooleanExpression movieNmContain(String movieNm) {
        return isEmpty(movieNm) ? null : jpaMovie.movieNm.contains(movieNm);
    }
    private BooleanExpression movieShowTmGoe(Integer showTm) {
        if(showTm == null)
            return null;
        else {
            return jpaMovie.showTm.goe(showTm);
        }
    }
    private BooleanExpression movieOpenDtGoe(Integer openDt) {
        if(openDt == null)
            return null;
        else {
            return jpaMovie.openDt.goe(openDt);
        }
    }
    private BooleanExpression moviePrdtStatNmEq(String prdtStatNm) {
        return isEmpty(prdtStatNm) ? null : jpaMovie.prdtStatNm.eq(prdtStatNm);
    }
    private BooleanExpression movieTypeNmEq(String typeNm) {
        return isEmpty(typeNm) ? null : jpaMovie.typeNm.eq(typeNm);
    }
    private BooleanExpression movieNationNmEq(String nationNm) {
        return isEmpty(nationNm) ? null : jpaMovie.nationNm.eq(nationNm);
    }
    private BooleanExpression movieDirectorNmContain(String directorNm) {
        return isEmpty(directorNm) ? null : jpaMovie.directorNm.contains(directorNm);
    }
    private BooleanExpression movieWatchGradeNmEq(String watchGradeNm) {
        return isEmpty(watchGradeNm) ? null : jpaMovie.watchGradeNm.eq(watchGradeNm);
    }
    private BooleanExpression movieTopScoreGoe(Integer topScore) {
        if(topScore == null)
            return null;
        else {
            return jpaMovie.openDt.goe(topScore);
        }
    }
}
