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
    private String site;
    private String key;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private JpaMovie movie;
    @Builder
    public Video(String name, String site, String key, String type, JpaMovie movie) {
        this.name = name;
        this.site = site;
        this.key = key;
        this.type = type;
        this.movie = movie;
    }

}
