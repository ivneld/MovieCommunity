package Movie.MovieCommunity.JPADomain.dto;


import lombok.Data;

@Data
public class TvGenreDto {
    private Long id;
    private String genreNm;

    public TvGenreDto() {
    }

    public TvGenreDto(Long id, String genreNm) {
        this.id = id;
        this.genreNm = genreNm;
    }
}
