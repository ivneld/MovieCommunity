package Movie.MovieCommunity.JPARepository;

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
        return queryFactory.select(new QMovieWithGenreCountDao(genre.id, genre.genreNm, movie.id, movie.popularity))
                .from(jpaMovieWithGenre)
                .join(genre).on(genre.id.eq(jpaMovieWithGenre.genre.id))
                .join(movie).on(movie.id.eq(jpaMovieWithGenre.movie.id))
                .orderBy(movie.popularity.desc()).limit(100)
                .fetch();
    }

    @Override
    public List<MovieWithGenreDao> findMovieByGenre(Long genreId) {
        return queryFactory.select(new QMovieWithGenreDao(movie.movieCd, movie.movieNm, movie.showTm, movie.openDt, movie.prdtStatNm, movie.typeNm,
                        movie.nationNm, movie.directorNm, movie.auditNo, movie.watchGradeNm, movie.topScore, movie.salesAcc,
                        movie.audiAcc, movie.tmId, movie.overview, movie.backdropPath, movie.posterPath, movie.popularity,
                        movie.voteAverage, movie.voteCount, movie.collectionId, movie.seriesName, movie.collectionBackdropPath, movie.collectionPosterPath))
                .from(jpaMovieWithGenre)
                .join(genre).on(genre.id.eq(jpaMovieWithGenre.genre.id))
                .join(movie).on(movie.id.eq(jpaMovieWithGenre.movie.id))
                .where(genre.id.eq(genreId))
                .fetch();
    }


}
