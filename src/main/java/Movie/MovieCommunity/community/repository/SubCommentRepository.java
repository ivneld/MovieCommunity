package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
    List<SubComment> findAllByMember(Member member);

}