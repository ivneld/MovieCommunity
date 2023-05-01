package Movie.MovieCommunity.JPADomain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieVideo{
    private String name;
    private String site;
    private String key;
    private String type;
    @Builder
    public MovieVideo(String name, String site, String key, String type) {
        this.name = name;
        this.site = site;
        this.key = key;
        this.type = type;
    }
}