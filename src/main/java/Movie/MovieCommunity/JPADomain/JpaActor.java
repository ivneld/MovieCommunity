package Movie.MovieCommunity.JPADomain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "actor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaActor {
    public JpaActor(String actorNm) {
        this.actorNm = actorNm;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;

    @Column(nullable = false)
    private String actorNm;

    @Override
    public String toString() {
        return "JpaActor{" +
                "id=" + id +
                ", actorNm='" + actorNm + '\'' +
                ", topMovieCnt=" + topMovieCnt +
                '}';
    }

    //    peopleNmEn	문자열	배우명(영문)을 출력합니다.
    //private String cast;//	문자열	배역명을 출력합니다.
    private Integer topMovieCnt;

    @OneToMany(mappedBy = "actor")
    private List<JpaMovieWithActor> movieWithActors = new ArrayList<>();

/*    private String peopleCd;//	문자열	영화인 코드를 출력합니다.
    private String actorNm;//	문자열	영화인명을 출력합니다.
    //peopleNmEn	문자열	영화인명(영문)을 출력합니다.
    private String sex;//	문자열	성별을 출력합니다.
    repRoleNm	문자열	영화인 분류명을 출력합니다.
    filmos	문자열	영화인 필모를 나타냅니다.
    movieCd	문자열	참여 영화코드를 출력합니다.
    movieNm	문자열	참여 영화명을 출력합니다.
    moviePartNm	문자열	참여분야를 나타냅니다.
    homepages	문자열	관련 URL을 출력합니다.*/

    public Integer countTopMovieCnt() {
        this.topMovieCnt += 1;
        return topMovieCnt;
    }
}
