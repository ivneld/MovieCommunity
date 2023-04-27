package Movie.MovieCommunity.JPADomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private String value;
}
