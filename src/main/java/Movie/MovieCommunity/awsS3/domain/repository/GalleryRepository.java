package Movie.MovieCommunity.awsS3.domain.repository;


import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {
    @Override
    List<GalleryEntity> findAll();
}
