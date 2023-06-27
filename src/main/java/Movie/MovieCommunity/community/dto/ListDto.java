package Movie.MovieCommunity.community.dto;

import Movie.MovieCommunity.community.domain.Posts;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Data
@Setter
@NoArgsConstructor
public class ListDto {
    private UserDto.Response user;
    private Page<Posts> postsList;
    private int PreviousPageNumber;
    private int NextPageNumber;
    private boolean hasPreviousPage;
    private boolean hasNextPage;


    public ListDto( Page<Posts> postsList, int previousPageNumber, int nextPageNumber, boolean hasPreviousPage, boolean hasNextPage) {
        this.postsList = postsList;
        PreviousPageNumber = previousPageNumber;
        NextPageNumber = nextPageNumber;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
    }
}
