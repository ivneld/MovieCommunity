package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import lombok.Data;

@Data
public class ShortCommentDto {
    private Long id;
    private String name;
    private String content;
    private int like;
}
