package Movie.MovieCommunity.community.repository;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    @Modifying
    @Query("update Posts p set p.likeCount = p.likeCount + 1 where p = :posts")
    int updateAddCount(@Param("posts") Posts posts);

    @Modifying
    @Query("update Posts p set p.likeCount = p.likeCount - 1 where p = :posts")
    int updateSubtractCount(@Param("posts") Posts posts);



    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);
    List<Posts> findByTitleContaining(String keyword);

    Page<Posts> findByMovieId(Long movieId,  Pageable pageable);
    List<Posts> findByMovieId(Long movieId);


    Page<Posts> findByUser(Member user, Pageable pageable);
    List<Posts> findByUser(Member user);
}
