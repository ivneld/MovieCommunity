package Movie.MovieCommunity.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MovieWithGenre {
    private Long id;
    private Long movieId;
    private Long genreId;
}
