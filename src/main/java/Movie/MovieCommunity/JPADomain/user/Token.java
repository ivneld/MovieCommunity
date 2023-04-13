package Movie.MovieCommunity.JPADomain.user;

import Movie.MovieCommunity.JPADomain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
public class Token extends BaseTimeEntity {
    @Id
    @Column(name = "user_email", length = 255 , nullable = false)
    private String userEmail;

    @Column(name = "refresh_token", length = 255 , nullable = false)
    private String refreshToken;

    public Token(){}

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Builder
    public Token(String userEmail, String refreshToken) {
        this.userEmail = userEmail;
        this.refreshToken = refreshToken;
    }
}
