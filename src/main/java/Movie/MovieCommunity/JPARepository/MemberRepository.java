package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);
    boolean existsByEmail(String email);
    Member findByNickname(String nickname);

}
