package Movie.MovieCommunity.community.dto;


import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class DetailPageDto {

    private String nickname;
    private String title;
    private String content;
    private List<GalleryEntity> gallery;

}
