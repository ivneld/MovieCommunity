package Movie.MovieCommunity.repository;

import Movie.MovieCommunity.domain.EtcData;
import Movie.MovieCommunity.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    public Movie save(Movie movie);
    public Optional<Movie> findByMovieCd(String movieCd);
    public int update(Movie movieDto);



    public List<Movie> selectAll();
    public List<Movie> findByFilter(MovieSearchCond cond);
    public List<Movie> findByPageNum(List<Movie> filteredMovie , int page);

    public int setEtcData(String movieCd, EtcData etcData);
}
