package Movie.MovieCommunity.awsS3.domain.entity;

import Movie.MovieCommunity.community.domain.Posts;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "gallery")
@Table(name = "gallery")
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "posts_id")
    private Posts posts;


    @Builder
    public GalleryEntity(Long id, String title, String filePath,Posts posts) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.posts=posts;
    }
}
