package Movie.MovieCommunity.community.response;

import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.domain.SubComment;
import Movie.MovieCommunity.community.dto.CommentDto;
import Movie.MovieCommunity.community.dto.GalleryDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponseDto {
    private Long id;
    private String author;
    private String subComment;
    //   private Comment comment; // (자식코멘트 조회할때 필요할 줄 알았지만, 자식코멘트에서 부모코멘트 볼 필요 없음)
    private int cntLikes;
    private String createdAt;
    private String modifiedAt;

    @Getter
    public static class Response {
        private final Long id;
        private final String author;
        private String subComment;
        private int cntLikes;
        private String createdAt;
        private String modifiedAt;

        /**
         /* Entity -> Dto*/
        public Response(SubComment subComment) {
            this.id = subComment.getId();
            this.author = subComment.getMember().getNickname();
            this.subComment = subComment.getSubComment();
            this.cntLikes = subComment.getLikeCount();
            this.createdAt = subComment.getCreatedDate();
            this.modifiedAt = subComment.getModifiedDate();

        }
    }
}
