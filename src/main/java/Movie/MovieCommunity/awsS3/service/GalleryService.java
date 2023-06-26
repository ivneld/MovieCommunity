package Movie.MovieCommunity.awsS3.service;


import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.awsS3.domain.repository.GalleryRepository;
import Movie.MovieCommunity.awsS3.dto.GalleryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GalleryService {
    private S3Service s3Service;
    private GalleryRepository galleryRepository;

    public void savePost(GalleryDto galleryDto) {
        galleryRepository.save(galleryDto.toEntity());
    }

    public List<GalleryDto> getList() {
        List<GalleryEntity> galleryEntityList = galleryRepository.findAll();
        List<GalleryDto> galleryDtoList = new ArrayList<>();

        for (GalleryEntity galleryEntity : galleryEntityList) {
            galleryDtoList.add(convertEntityToDto(galleryEntity));
        }

        return galleryDtoList;
    }

    private GalleryDto convertEntityToDto(GalleryEntity galleryEntity) {
        return GalleryDto.builder()
                .id(galleryEntity.getId())
                .title(galleryEntity.getTitle())
                .filePath(galleryEntity.getFilePath())
                .imgFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + galleryEntity.getFilePath())
                .build();
    }

    @Transactional
    public void delete(Long galleryId) {
        GalleryEntity gallery = galleryRepository.findById(galleryId).orElseThrow(() ->
                new IllegalArgumentException("해당 이미지가 존재하지 않습니다. id=" + galleryId));

        galleryRepository.delete(gallery);
    }

    @Transactional
    public GalleryDto read(Long galleryId) {
        GalleryEntity gallery = galleryRepository.findById(galleryId).orElseThrow(() ->
                new IllegalArgumentException("해당 이미지가 존재하지 않습니다. id=" + galleryId));

        GalleryDto galleryDto = convertEntityToDto(gallery);
        return galleryDto;
    }
}
