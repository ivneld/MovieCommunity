package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPADomain.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="moviewithmember")
@Getter
@Table(name="moviewithmember")
@NoArgsConstructor
public class MovieWithMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_with_member_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;


}