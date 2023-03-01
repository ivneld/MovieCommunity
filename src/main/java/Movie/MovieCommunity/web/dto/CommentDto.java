package Movie.MovieCommunity.web.dto;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private Long memberId;
    private String memberName;

    public CommentDto(Long id, String content, Long memberId, String memberName) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
