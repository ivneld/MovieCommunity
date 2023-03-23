package Movie.MovieCommunity.JPARepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
import static Movie.MovieCommunity.JPADomain.QJpaWeeklyBoxOffice.jpaWeeklyBoxOffice;

public class WeeklyBoxOfficeRepositoryImpl implements WeeklyBoxOfficeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public WeeklyBoxOfficeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tuple> movieWithWeekly() {
        return queryFactory.select(jpaMovie, jpaWeeklyBoxOffice)
                .from(jpaMovie)
                .leftJoin(jpaWeeklyBoxOffice).on(jpaMovie.movieCd.eq(jpaWeeklyBoxOffice.movieCd))
                .fetch();
    }
}
