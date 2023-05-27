package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="moviewithcompany")
@Getter
@NoArgsConstructor
public class JpaMovieWithCompany {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_with_company_id")
    private Long id;

    public JpaMovieWithCompany(Movie movie, Company company) {
        this.movie = movie;
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="company_id")
    private Company company;
}
