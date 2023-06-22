package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.web.apiDto.movie.entityDto.LikeGenreDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MovieLikeGenreResponse {
    List<LikeGenreDto> genres = new ArrayList<>();

}
