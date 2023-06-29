package Movie.MovieCommunity.web.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long memberId;
    private String username;
    private Long movieId;
    private String content;
    private Integer likeCount;
    private boolean myLike;

    @Builder
    public CommentResponse(Long commentId, Long memberId, String username, Long movieId, String content, Integer likeCount, boolean myLike) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.username = username;
        this.movieId = movieId;
        this.content = content;
        this.likeCount = likeCount;
        this.myLike = myLike;
    }
}
