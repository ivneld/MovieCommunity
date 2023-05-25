package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.QJpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreCountDao;
import Movie.MovieCommunity.JPADomain.dto.QMovieGenreDto;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreDao;
import Movie.MovieCommunity.JPARepository.dao.QMovieWithGenreCountDao;
import Movie.MovieCommunity.JPARepository.dao.QMovieWithGenreDao;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static Movie.MovieCommunity.JPADomain.QGenre.genre;
import static Movie.MovieCommunity.JPADomain.QJpaMovieWithGenre.jpaMovieWithGenre;
import static Movie.MovieCommunity.JPADomain.QMovie.movie;

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
        return queryFactory.select(new QMovieGenreDto(movie.id, movie.movieNm, movie.openDt,
                        movie.audiAcc, genre.id, genre.genreNm))
                .from(jpaMovieWithGenre)
                .leftJoin(movie).on(movie.id.eq(jpaMovieWithGenre.movie.id))
                .leftJoin(genre).on(genre.id.eq(jpaMovieWithGenre.genre.id))
                .fetch();

    }

    @Override
    public List<MovieWithGenreCountDao> findTop100MovieWithGenre() {
        return queryFactory.select(new QMovieWithGenreCountDao(jpaGenre.id, jpaGenre.genreNm, jpaMovie.id, jpaMovie.popularity))
                .from(jpaMovieWithGenre)
                .join(jpaGenre).on(jpaGenre.id.eq(jpaMovieWithGenre.genre.id))
                .join(jpaMovie).on(jpaMovie.id.eq(jpaMovieWithGenre.movie.id))
                .orderBy(jpaMovie.popularity.desc()).limit(100)
                .fetch();
    }

    @Override
    public List<MovieWithGenreDao> findMovieByGenre(Long genreId) {
        return queryFactory.select(new QMovieWithGenreDao(jpaMovie.movieCd, jpaMovie.movieNm, jpaMovie.showTm, jpaMovie.openDt, jpaMovie.prdtStatNm, jpaMovie.typeNm,
                        jpaMovie.nationNm, jpaMovie.directorNm, jpaMovie.auditNo, jpaMovie.watchGradeNm, jpaMovie.topScore, jpaMovie.salesAcc,
                        jpaMovie.audiAcc, jpaMovie.tmId, jpaMovie.overview, jpaMovie.backdropPath, jpaMovie.posterPath, jpaMovie.popularity,
                        jpaMovie.voteAverage, jpaMovie.voteCount, jpaMovie.collectionId, jpaMovie.seriesName, jpaMovie.collectionBackdropPath, jpaMovie.collectionPosterPath))
                .from(jpaMovieWithGenre)
                .join(jpaGenre).on(jpaGenre.id.eq(jpaMovieWithGenre.genre.id))
                .join(jpaMovie).on(jpaMovie.id.eq(jpaMovieWithGenre.movie.id))
                .where(jpaGenre.id.eq(genreId))
                .fetch();
    }


}
