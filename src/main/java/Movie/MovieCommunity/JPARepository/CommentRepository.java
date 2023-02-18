package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
