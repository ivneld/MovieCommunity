package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<JpaCompany, Long> {
}
