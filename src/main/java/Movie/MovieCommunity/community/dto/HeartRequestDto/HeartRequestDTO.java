package Movie.MovieCommunity.community.dto.HeartRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class HeartRequestDTO {
    private Long memberId;
    private Long postsId;

    public HeartRequestDTO(Long memberId, Long postsId) {
        this.memberId = memberId;
        this.postsId = postsId;
    }
}