package Movie.MovieCommunity.community.dto;

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
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int totalPagesCount;


    public ListDto(List<PostsDto.Total> postsList, int totalPagesCount, int previousPageNumber, int nextPageNumber, boolean hasPreviousPage, boolean hasNextPage) {
        this.postsList = postsList;
        PreviousPageNumber = previousPageNumber;
        NextPageNumber = nextPageNumber;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        this.totalPagesCount = totalPagesCount;
    }
}
