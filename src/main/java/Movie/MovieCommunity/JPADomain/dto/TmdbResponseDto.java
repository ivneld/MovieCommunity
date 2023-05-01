package Movie.MovieCommunity.JPADomain.dto;

import Movie.MovieCommunity.JPADomain.Video;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TmdbResponseDto {
    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private String backdropPath;
    private String posterPath;
    private float popularity;
    private float voteAverage;
    private int voteCount;

    private int collectionId;
    private String seriesName;
    private String collectionBackdropPath;
    private String collectionPosterPath;
    private List<Video> videos = new ArrayList<>();
    @Builder
    public TmdbResponseDto(int id, String title, String overview, String releaseDate, String backdropPath, String posterPath, float popularity, float voteAverage, int voteCount) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public void addVideos(List<Video> tmdbTests) {
        this.videos.addAll(tmdbTests);
    }

}