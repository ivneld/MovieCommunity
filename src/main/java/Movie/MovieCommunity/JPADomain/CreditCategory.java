package Movie.MovieCommunity.JPADomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum CreditCategory {
    ACTOR("actor"),
    CREW("crew");
    private String type;
}
