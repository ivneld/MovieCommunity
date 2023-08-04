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

    private List<PostsDto.Total> posts;
    private Integer totalPostCount;
    public DetailPageDto(List<PostsDto.Total> posts, Integer totalPostCount){
        this.posts = posts;
        this.totalPostCount = totalPostCount;
    }
}
