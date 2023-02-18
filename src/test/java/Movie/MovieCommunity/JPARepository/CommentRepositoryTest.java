package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.web.form.AddMemberForm;
import Movie.MovieCommunity.web.form.CommentForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
@Rollback(value = false)
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;
//    @PostConstruct
//    public void setting(){
//
//        Member member = new Member();
//    }
    @Test
    public void create(){
        Optional<Member> findMember = memberRepository.findById(1l);
        Member member = findMember.get();
        Optional<Board> findBoard = boardRepository.findById(1l);
        Board board = findBoard.get();
        CommentForm commentForm = new CommentForm("content",member, board);
        Comment comment = new Comment(commentForm);
        System.out.println("comment = " + comment);
        Comment save = commentRepository.save(comment);
    }

    @Test
    public void findOne(){
        Optional<Comment> findComment = commentRepository.findById(1l);
        Comment comment = findComment.get();
        System.out.println("comment = " + comment);
    }
}