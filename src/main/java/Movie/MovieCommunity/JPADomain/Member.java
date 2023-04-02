package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.web.form.AddMemberForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member  extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;

    private String provider;
    private String providerId;


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
//    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @Builder
    public Member(String username, String name, String email, String password, String provider, String providerId) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member(AddMemberForm memberForm) {
        this.name = memberForm.getName();
        this.email = memberForm.getEmail();
        this.password = memberForm.getPassword();
    }
}
