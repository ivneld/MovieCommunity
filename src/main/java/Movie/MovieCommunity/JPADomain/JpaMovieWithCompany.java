package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Data;
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

    public JpaMovieWithCompany(JpaMovie movie, JpaCompany company) {
        this.movie = movie;
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private JpaMovie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private JpaCompany company;
}
