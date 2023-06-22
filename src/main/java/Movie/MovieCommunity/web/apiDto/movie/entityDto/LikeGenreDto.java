package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import lombok.Data;

@Data
public class LikeGenreDto {
    String genreNm;
    Long cnt;

    public LikeGenreDto(String genreNm, Long cnt) {
        this.genreNm = genreNm;
        this.cnt = cnt;
    }
}
