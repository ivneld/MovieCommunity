package Movie.MovieCommunity.community.dto.HeartRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class SubCommentHeartRequestDTO {
    private Long memberId;
    private Long subCommentId;

    public SubCommentHeartRequestDTO(Long memberId, Long subCommentId) {
        this.memberId = memberId;
        this.subCommentId = subCommentId;
    }
}
