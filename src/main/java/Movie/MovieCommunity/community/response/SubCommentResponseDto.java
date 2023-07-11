package Movie.MovieCommunity.community.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponseDto {
    private Long id;
    private String author;
    private String subComment;
    //   private Comment comment; // (자식코멘트 조회할때 필요할 줄 알았지만, 자식코멘트에서 부모코멘트 볼 필요 없음)
    private int cntLikes;
    private String createdAt;
    private String modifiedAt;
}
