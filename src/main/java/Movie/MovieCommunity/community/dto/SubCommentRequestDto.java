package Movie.MovieCommunity.community.dto;

import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentRequestDto {
    private Long commentId;
    private String subComment;
}