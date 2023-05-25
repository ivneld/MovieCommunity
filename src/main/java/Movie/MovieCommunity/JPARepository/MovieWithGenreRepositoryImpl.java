package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import Movie.MovieCommunity.JPADomain.dto.QMovieGenreDto;
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
}
