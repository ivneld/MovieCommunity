package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment write(CommentForm commentForm){
        return commentRepository.save(new Comment(commentForm));
    }
    public Comment update(CommentForm commentForm){
        Optional<Comment> findComment = commentRepository.findById(commentForm.getId());
        if (findComment.isPresent()){
            Comment comment = findComment.get();
            comment.updateContent(comment.getContent());
        }
        return null;
    }

}
