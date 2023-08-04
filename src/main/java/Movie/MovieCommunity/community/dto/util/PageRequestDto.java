package Movie.MovieCommunity.community.dto.util;

import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {
    private int page;
    private int size;
}
