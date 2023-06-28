package Movie.MovieCommunity.awsS3.domain.repository;


import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {
    @Override
    List<GalleryEntity> findAll();

    Optional<GalleryEntity> findById(Long id);

    @Modifying
    @Query("update gallery p set p.posts = :posts where p.id = :id")
    int updatePosts(@Param("id") Long id,@Param("posts") Posts posts);

}
