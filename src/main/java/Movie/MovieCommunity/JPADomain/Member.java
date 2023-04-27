package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.user.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member  extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    @Email
//    @Column(nullable = false)
    private String email;

//    @Column(nullable = false)
    private Boolean emailVerified = false;
    private String nickname;
    private String password;
    private String name;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Authority authority;
//    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String providerId;


    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
//    @OneToMany(mappedBy = "member")
//    private List<LikeBoard> likeBoards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<LikeMovie> likeMovies = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, Authority authority, Provider provider, String providerId, String imageUrl){
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.authority = authority;
    }


    public Member(String email, String password, String nickname, Authority authority) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

//    public void updateLikeBoard(LikeBoard likeBoard){
//        this.likeBoards.add(likeBoard);
//        likeBoard.
//    }

}
