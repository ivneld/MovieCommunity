package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import lombok.Builder;
import lombok.Data;

@Data
public class SeriesDto {
    private Integer id;
    private String imageUrl;
    private String title;
    private Integer year;
    @Builder
    public SeriesDto(Integer id, String imageUrl, String title, Integer year) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.year = year;
    }
}
