package Movie.MovieCommunity.JPADomain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class MovieWithCredit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_with_credit_id")
    private Long id;

    public void updateData(Movie movie, String cast) {
        this.movie = movie;
        this.cast = cast;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credit credit;
    private String cast;

    @Override
    public String toString() {
        return "JpaMovieWithActor{" +
                "id=" + id +
                ", movie=" + movie +
                ", actor=" + credit +
                ", cast='" + cast + '\'' +
                '}';
    }
//    public void setMovieId(Long movieId){
//        this.movieId = movieId;
//    }
    @Builder
    public MovieWithCredit(Movie movie, Credit credit, String cast) {
        this.movie = movie;
        this.credit = credit;
        this.cast = cast;
    }
}
