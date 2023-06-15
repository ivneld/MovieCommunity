package Movie.MovieCommunity.JPADomain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity(name="video")
@Getter
@Table(name="video")
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="video_id")
    private Long id;
    private String name;
    private String siteName;
    private String url;
    private String videoType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @Builder
    public Video(String name, String siteName, String url, String videoType, Movie movie) {
        this.name = name;
        this.siteName = siteName;
        this.url = url;
        this.videoType = videoType;
        this.movie = movie;
    }

}
