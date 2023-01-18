package Movie.MovieCommunity.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MovieWithActor {
    private Long id;
    private Long movieId;
    private Long ActorId;
    private String cast;
//    public void setMovieId(Long movieId){
//        this.movieId = movieId;
//    }

}
