package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@Table(name="moviewithgenre")
@NoArgsConstructor
public class JpaMovieWithGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_with_genre_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id")
    private Genre genre;
    public JpaMovieWithGenre(Movie movie, Genre genre) {
        this.movie = movie;
        this.genre = genre;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
