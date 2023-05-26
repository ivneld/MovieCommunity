package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
