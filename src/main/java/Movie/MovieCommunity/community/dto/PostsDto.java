package Movie.MovieCommunity.community.dto;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.Posts;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class PostsDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestParam{
        private String title;
        private String content;
        private List<Long> galleryIds;
        private Long movieId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailRequestParam{
        private String title;
        private String content;
        Optional<List<Long>> GalleryId;
        private Long movieId;
    }




    /** 게시글의 등록과 수정을 처리할 요청(Request) 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long id;
        private String title;
        private String writer;
        private String content;
        private String createdDate, modifiedDate;
        private int view;
        private Member user;
        private Movie movie;
        private List<GalleryEntity> gallery;


        /* Dto -> Entity */
        public Posts toEntity() {
            Posts posts = Posts.builder()
                    .id(id)
                    .title(title)
                    .writer(writer)
                    .content(content)
                    .view(0)
                    .user(user)
                    .movie(movie)
                    .galleries(gallery)
                    .build();

            return posts;
        }
    }

    /**
     * 게시글 정보를 리턴할 응답(Response) 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */
    @Getter
    public static class Response {
        private final Long id;
        private final String title;
        private final String writer;
        private final String content;
        private final String createdDate, modifiedDate;
        private final int view;
        private final int likeCount;
        private final Long movieId;
        private final Long userId;
        private final List<CommentDto.Response> comments;
        private final List<GalleryDto.Response> galleries;

        /**
        /* Entity -> Dto*/
        public Response(Posts posts) {
            this.id = posts.getId();
            this.title = posts.getTitle();
            this.writer = posts.getWriter();
            this.content = posts.getContent();
            this.createdDate = posts.getCreatedDate();
            this.modifiedDate = posts.getModifiedDate();
            this.view = posts.getView();
            this.likeCount = posts.getLikeCount();
            this.movieId = posts.getMovie().getId();
            this.userId = posts.getUser().getId();
            this.comments = posts.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.galleries = posts.getGalleries().stream().map(GalleryDto.Response::new).collect(Collectors.toList());
        }
    }


    @Getter
    public static class Total {
        private final Long id;
        private final String title;
        private final String writer;
        private final String content;
        private final int view;
        private final int likeCount;
        private final String moviePosterPath;
        private final Long commentsCount;

        /**
         /* Entity -> Dto*/
        public Total(Posts posts) {
            this.id = posts.getId();
            this.title = posts.getTitle();
            this.writer = posts.getWriter();
            this.content = posts.getContent();
            this.view = posts.getView();
            this.likeCount = posts.getLikeCount();
            this.moviePosterPath = posts.getMovie().getPosterPath();
            Stream<Comment> commentStream = posts.getComments().stream().filter((comment) -> comment.getSubComments() != null);
            AtomicInteger size = new AtomicInteger();
            commentStream.forEach(comment -> {
                size.addAndGet(comment.getSubComments().size());

            });
            this.commentsCount = posts.getComments().stream().count() + size.get();
        }
    }
}
