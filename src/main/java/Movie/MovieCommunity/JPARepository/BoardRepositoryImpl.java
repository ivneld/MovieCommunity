//package Movie.MovieCommunity.JPARepository;
//
//import Movie.MovieCommunity.JPARepository.dao.BoardDao;
//import Movie.MovieCommunity.JPARepository.dao.QBoardDao;
//import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//import org.thymeleaf.util.StringUtils;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
////import static Movie.MovieCommunity.JPADomain.QBoard.board;
//import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
//import static Movie.MovieCommunity.JPADomain.QMember.member;
//import static org.thymeleaf.util.StringUtils.isEmpty;
//
//
//@Repository
//public class BoardRepositoryImpl implements QuerydslBoardRepository{
//    private final EntityManager em;
//    private final JPAQueryFactory queryFactory;
//
//    public BoardRepositoryImpl(EntityManager em) {
//        this.em = em;
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
////    @Override
////    public Page<BoardDao> searchBoardList(BoardSearchCond boardSearchCond, Pageable pageable) {
////        List<BoardDao> content = queryFactory.select(new QBoardDao(
////                                board.id, board.title, board.content, board.likeCnt,
////                                member.id.as("memberId"), member.name.as("memberNm"),
////                                jpaMovie.id.as("movieId"), jpaMovie.movieNm
////                        )
////                ).from(board).
////                join(board.member, member).
////                join(board.movie, jpaMovie).
////                where(movieNmContain(boardSearchCond.getMovieNm()))
////                .offset(pageable.getOffset())
////                .limit(pageable.getPageSize())
////                .orderBy(board.createdDt.desc())
////                .fetch();
////
////        Long count = queryFactory.select(board.count())
////                .from(board)
////                .join(board.member, member)
////                .join(board.movie, jpaMovie)
////                .where(movieNmContain(boardSearchCond.getMovieNm()))
////                .fetchFirst();
////
////
////        return new PageImpl<>(content, pageable, count);
////    }
//
//    private BooleanExpression movieNmContain(String movieNm) {
//        return isEmpty(movieNm) ? null : jpaMovie.movieNm.contains(movieNm);
//    }
//}
