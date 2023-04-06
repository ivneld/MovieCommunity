package Movie.MovieCommunity.JPARepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public interface WeeklyBoxOfficeRepositoryCustom {

    List<Tuple> movieWithWeekly();
}
