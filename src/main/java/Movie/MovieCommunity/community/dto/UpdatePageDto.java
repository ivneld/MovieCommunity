package Movie.MovieCommunity.community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
public class UpdatePageDto {
    private UserDto.Response user;
    private PostsDto.Response posts;
}
