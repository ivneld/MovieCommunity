package Movie.MovieCommunity.JPADomain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
peopleCd	문자열	영화인 코드를 출력합니다.
peopleNm	문자열	영화인명을 출력합니다.
peopleNmEn	문자열	영화인명(영문)을 출력합니다.
sex	문자열	성별을 출력합니다.
repRoleNm	문자열	영화인 분류명을 출력합니다.
filmos	문자열	영화인 필모를 나타냅니다.
movieCd	문자열	참여 영화코드를 출력합니다.
movieNm	문자열	참여 영화명을 출력합니다.
moviePartNm	문자열	참여분야를 나타냅니다.
homepages	문자열	관련 URL을 출력합니다.
 */

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Credit {
    public Credit(String actorNm) {
        this.actorNm = actorNm;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long id;
    private Long tmCreditId;

    @Column(nullable = false)
    private String actorNm;
    private String profile_path;
    @Enumerated(EnumType.STRING)
    private CreditCategory creditCategory;
    private Integer topMovieCnt = 0;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieWithCredit> movieWithCredits = new ArrayList<>();


    public Integer countTopMovieCnt() {
        this.topMovieCnt += 1;
        return topMovieCnt;
    }
    @Override
    public String toString() {
        return "JpaActor{" +
                "id=" + id +
                ", actorNm='" + actorNm + '\'' +
                ", topMovieCnt=" + topMovieCnt +
                '}';
    }
    @Builder
    public Credit(Long tmCreditId, String actorNm, String profile_path, CreditCategory creditCategory) {
        this.tmCreditId = tmCreditId;
        this.actorNm = actorNm;
        this.profile_path = profile_path;
        this.creditCategory = creditCategory;
    }
    public MovieWithCredit addMovieWithActor(Movie movie, String cast){
        MovieWithCredit ma = MovieWithCredit.builder()
                .movie(movie)
                .credit(this)
                .cast(cast).build();
        movieWithCredits.add(ma);
        return ma;
    }
}
