package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaGenre;
import Movie.MovieCommunity.JPADomain.QJpaGenre;
import Movie.MovieCommunity.JPADomain.QJpaMovie;
import Movie.MovieCommunity.JPADomain.QJpaMovieWithGenre;
import Movie.MovieCommunity.JPADomain.dto.MovieFilterDto;
import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import Movie.MovieCommunity.JPADomain.dto.QMovieFilterDto;
import Movie.MovieCommunity.JPADomain.dto.QMovieGenreDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaGenre.*;
import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
import static Movie.MovieCommunity.JPADomain.QJpaMovieWithGenre.*;

public class MovieWithGenreRepositoryImpl implements MovieWithGenreRepositoryCustom{

    /**
     * movie_nm, openDt, audiAcc, movie_id, genre_id, genre_nm
     */

    private final JPAQueryFactory queryFactory;
    public MovieWithGenreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MovieGenreDto> findAllMovieAndGenre() {
        return queryFactory.select(new QMovieGenreDto(jpaMovie.id, jpaMovie.movieNm, jpaMovie.openDt,
                        jpaMovie.audiAcc, jpaGenre.id, jpaGenre.genreNm))
                .from(jpaMovieWithGenre)
                .leftJoin(jpaMovie).on(jpaMovie.id.eq(jpaMovieWithGenre.movie.id))
                .leftJoin(jpaGenre).on(jpaGenre.id.eq(jpaMovieWithGenre.genre.id))
                .fetch();

    }

    /**
     * 년도별, 장르별
     */
    @Override
    public List<MovieFilterDto> movieWithGenre() {
        return queryFactory.select(new QMovieFilterDto(jpaMovie.id, jpaGenre.id, jpaMovie.movieNm, jpaMovie.showTm, jpaMovie.openDt, jpaMovie.typeNm, jpaMovie.nationNm, jpaMovie.directorNm,
                        jpaMovie.auditNo, jpaMovie.watchGradeNm, jpaMovie.topScore, jpaMovie.salesAcc, jpaMovie.audiAcc, jpaGenre.genreNm))
                .from(jpaMovieWithGenre)
                .leftJoin(jpaMovie).on(jpaMovie.id.eq(jpaMovieWithGenre.movie.id))
                .leftJoin(jpaGenre).on(jpaGenre.id.eq(jpaMovieWithGenre.genre.id))
                .fetch();
    }






}
