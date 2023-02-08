package Movie.MovieCommunity.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddMemberForm {
    @NotEmpty(message = "필수 값입니다.")
    private String name;
    @NotEmpty(message = "필수 값입니다.")
    private String email;
    @NotEmpty(message = "필수 값입니다.")
    private String password;
}
