package Movie.MovieCommunity.community.dto;

import Movie.MovieCommunity.community.domain.Posts;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@Setter
public class SearchPageDto {
    private UserDto.Response user;
    private Page<Posts> searchList;
    private String keyword;
    private int PreviousPageNumber;
    private int NextPageNumber;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

}
