package Movie.MovieCommunity.awsS3.dto;


import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GalleryDto {
    private Long id;
    private String title;
    private String filePath;
    private String imgFullPath;

    public GalleryEntity toEntity(){
        GalleryEntity build = GalleryEntity.builder()
                .id(id)
                .title(title)
                .filePath(imgFullPath)
           //     .imgFullPath(imgFullPath)
                .build();
        return build;
    }

    @Builder
    public GalleryDto(Long id, String title, String filePath, String imgFullPath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }
}
