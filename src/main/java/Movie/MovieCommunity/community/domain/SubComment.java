package Movie.MovieCommunity.community.domain;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.dto.SubCommentRequestDto;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id", nullable = false)  //테이블끼리 매핑시켜줌
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @OneToMany(mappedBy = "subComment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubCommentLike> subCommentLikes;

    @Column(nullable = false)
    private String subComment; // 대댓글

    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.subComment = subCommentRequestDto.getSubComment();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}