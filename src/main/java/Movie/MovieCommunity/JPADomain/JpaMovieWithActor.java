package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="moviewithactor")
@Getter
@Setter
@Table(name="moviewithactor")
public class JpaMovieWithActor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_with_actor_id")
    private Long id;

    public void updateData(JpaMovie movie, String cast) {
        this.movie = movie;
        this.cast = cast;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private JpaMovie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private JpaActor actor;
    private String cast;

    @Override
    public String toString() {
        return "JpaMovieWithActor{" +
                "id=" + id +
                ", movie=" + movie +
                ", actor=" + actor +
                ", cast='" + cast + '\'' +
                '}';
    }
//    public void setMovieId(Long movieId){
//        this.movieId = movieId;
//    }

}
