package Movie.MovieCommunity.web.apiDto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCommentDto {
    private Long commentId;
    private Long movieId;
    private String movieNm;
    private String content;
    private LocalDateTime modifiedDt;
    @Builder

    public MyCommentDto(Long commentId, Long movieId, String movieNm, String content, LocalDateTime modifiedDt) {
        this.commentId = commentId;
        this.movieId = movieId;
        this.movieNm = movieNm;
        this.content = content;
        this.modifiedDt = modifiedDt;
    }
}
