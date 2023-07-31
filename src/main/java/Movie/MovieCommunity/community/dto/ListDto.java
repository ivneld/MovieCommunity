package Movie.MovieCommunity.community.dto;

import Movie.MovieCommunity.community.domain.Posts;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Setter
@NoArgsConstructor
public class ListDto {
//    private UserDto.Response user;
    private List<PostsDto.Total> postsList;
    private int PreviousPageNumber;
    private int NextPageNumber;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int totalPostsCount;


    public ListDto( List<PostsDto.Total> postsList, int totalPostsCount, int previousPageNumber, int nextPageNumber, boolean hasPreviousPage, boolean hasNextPage) {
        this.postsList = postsList;
        PreviousPageNumber = previousPageNumber;
        NextPageNumber = nextPageNumber;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        this.totalPostsCount = totalPostsCount;
    }
}
