package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Data;
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
    @JoinColumn(name="movie_id",nullable = false)
    private JpaMovie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id",nullable = false)
    private JpaGenre genre;
    public JpaMovieWithGenre(JpaMovie movie, JpaGenre genre) {
        this.movie = movie;
        this.genre = genre;
    }

    public void setMovie(JpaMovie movie) {
        this.movie = movie;
    }
}
