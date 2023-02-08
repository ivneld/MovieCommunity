package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieWithCompanyRepository extends JpaRepository<JpaMovieWithCompany, Long> {
}
