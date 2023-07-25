package Movie.MovieCommunity.community.domain;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.dto.SubCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)//테이블
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id", nullable = false)  //테이블끼리 매핑시켜줌
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;


    @OneToMany(mappedBy = "subComment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubCommentLike> subCommentLikes;

    @Column(nullable = false)
    private String subComment; // 대댓글


    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCount;


    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.subComment = subCommentRequestDto.getSubComment();
    }

}