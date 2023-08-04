package Movie.MovieCommunity.community.dto.HeartRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class CommentHeartRequestDTO {
    private Long memberId;
    private Long commentId;

    public CommentHeartRequestDTO(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
