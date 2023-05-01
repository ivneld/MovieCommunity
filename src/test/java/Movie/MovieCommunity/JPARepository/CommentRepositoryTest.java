//package Movie.MovieCommunity.JPARepository;
//
//import Movie.MovieCommunity.JPADomain.Board;
//import Movie.MovieCommunity.JPADomain.Comment;
//import Movie.MovieCommunity.JPADomain.Member;
//import Movie.MovieCommunity.web.dto.CommentDto;
//import Movie.MovieCommunity.web.form.CommentForm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Transactional
//@SpringBootTest
//@Rollback(value = false)
//class CommentRepositoryTest {
//    @Autowired
//    EntityManager em;
//    @Autowired
//    CommentRepository commentRepository;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    BoardRepository boardRepository;
////    @PostConstruct
////    public void setting(){
////
////        Member member = new Member();
////    }
//    @Test
//    public void create(){
//        Optional<Member> findMember1 = memberRepository.findById(1l);
//        Optional<Member> findMember2 = memberRepository.findById(2l);
//        Member member1 = findMember1.get();
//        Member member2 = findMember2.get();
//
//
//        Optional<Board> findBoard = boardRepository.findById(1l);
//        Board board = findBoard.get();
//
//
//        Optional<Comment> findComment = commentRepository.findById(12l);
//        Comment parent = findComment.get();
//
///*        for (int i=0;i<10;i++){
//            if(i%2==0){
//                CommentForm commentForm = new CommentForm("content"+i,member1, board, parent);
//                Comment comment = new Comment(commentForm);
//                commentRepository.save(comment);
//                em.flush();
//            }
//            else{
//                CommentForm commentForm = new CommentForm("content"+i,member2, board, parent);
//                Comment comment = new Comment(commentForm);
//                commentRepository.save(comment);
//                em.flush();
//            }
//        }*/
//    }
//
//    @Test
//    public void findOne(){
//        Optional<Comment> findComment = commentRepository.findById(1l);
//        Comment comment = findComment.get();
//        List<Comment> children = comment.getChildren();
//        System.out.println("comment = " + comment);
//    }
//
//    @Test
//    public void findChildrenComment(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDt").ascending());
//        Page<Comment> commentDtos = commentRepository.findByBoardIdAndParentIdIsNull(1l, pageable);
//
//        List<Comment> contents = commentDtos.getContent();
//
//
//        List<CommentDto> collect = contents.stream().map(comment -> new CommentDto(comment)).
//                collect(Collectors.toList());
//
//
//        int size = commentDtos.getSize();
//        System.out.println("size = " + size);
//        for (CommentDto content : collect) {
//            System.out.println("content.getContent() = " + content.getContent());
//            List<CommentDto> children = content.getChildren();
//            System.out.println("children = " + children);
//            for (CommentDto child : children) {
//                System.out.println("child = " + child);
//            }
//        }
//    }
//}