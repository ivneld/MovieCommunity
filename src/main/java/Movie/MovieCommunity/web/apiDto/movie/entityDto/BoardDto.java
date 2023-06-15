package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import lombok.Data;

@Data
public class BoardDto {
    private Long id;
    private String userImage;
    private String name;
    private String boardImage;
    private String title;
    private String content;
}
