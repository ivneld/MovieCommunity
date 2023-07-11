package Movie.MovieCommunity.community.dto;

import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.community.domain.Posts;
import lombok.*;

import java.util.stream.Collectors;

@Data
@Setter
@Getter
@NoArgsConstructor
public class GalleryDto {

    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String filePath;
        private Posts post;

        /**
         /* Entity -> Dto*/
        public Response(GalleryEntity gallery) {
            this.id = gallery.getId();
            this.title = gallery.getTitle();
            this.filePath = gallery.getFilePath();
            this.post = gallery.getPosts();
        }
    }

}
