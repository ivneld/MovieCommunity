package Movie.MovieCommunity.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@NoArgsConstructor
public class ListDto {
//    private UserDto.Response user;
    private List<PostsDto.Total> postsList;
    private int PreviousPageNumber;
    private int NextPageNumber;
    private int totalPagesCount;
    private int totalPostsCount;

    public ListDto(List<PostsDto.Total> postsList, int totalPagesCount, int previousPageNumber, int nextPageNumber) {
        this.postsList = postsList;
        this.PreviousPageNumber = previousPageNumber;
        this.NextPageNumber = nextPageNumber;
        this.totalPagesCount = totalPagesCount;
    }

    public ListDto(List<PostsDto.Total> postsList,int totalPagesCount, int previousPageNumber, int nextPageNumber, int totalPostsCount) {
        this.postsList = postsList;
        this.PreviousPageNumber = previousPageNumber;
        this.NextPageNumber = nextPageNumber;
        this.totalPagesCount = totalPagesCount;
        this.totalPostsCount = totalPostsCount;
    }
}
