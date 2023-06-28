package Movie.MovieCommunity.web.dto;

import Movie.MovieCommunity.JPADomain.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private Long memberId;
    private String nickname;
    private LocalDateTime modifiedDt;

    private boolean checkModified;
    private List<CommentDto> children = new ArrayList<>();

    public CommentDto(Long id, String content, Long memberId, String memberName) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.nickname = memberName;
    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.memberId = comment.getMember().getId();
        this.nickname = comment.getMember().getNickname();
        this.modifiedDt = comment.getModifiedDt();
        this.checkModified = checkedModified(comment.getCreatedDt(), comment.getModifiedDt());
        if (comment.getChildren().size() != 0) {
            this.children = comment.getChildren().
                    stream().map(children -> new CommentDto(children)).
                    collect(Collectors.toList());
        }
    }



    public boolean checkedModified(LocalDateTime createdDt, LocalDateTime modifiedDt){
        return !createdDt.isEqual(modifiedDt);
    }
}
