package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.web.form.AddMemberForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    public Member(AddMemberForm memberForm) {
        this.name = memberForm.getName();
        this.email = memberForm.getEmail();
        this.password = memberForm.getPassword();
    }
}
