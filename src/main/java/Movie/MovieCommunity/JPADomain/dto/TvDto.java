package Movie.MovieCommunity.JPADomain.dto;


import lombok.Data;

import java.util.List;

@Data
public class TvDto {

    private Long tvId;
    private String tvNm;
    private String originCountry;
    private String originLanguage;
    private String overview;
    private Double popularity;
    private String posterPath;
    private String backDropPath;
    private String firstAirDate;
    private Integer providerId;
    private List<TvGenreNameDto> genreDtos;
    private Double voteAverage;
    private Integer voteCount;

    public TvDto(Long tvId, String tvNm, String originCountry, String originLanguage, String overview, Double popularity, String posterPath, String backDropPath, String firstAirDate, Integer providerId, List<TvGenreNameDto> genreDtos, Double voteAverage, Integer voteCount) {
        this.tvId = tvId;
        this.tvNm = tvNm;
        this.originCountry = originCountry;
        this.originLanguage = originLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.firstAirDate = firstAirDate;
        this.providerId = providerId;
        this.genreDtos = genreDtos;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }
}
