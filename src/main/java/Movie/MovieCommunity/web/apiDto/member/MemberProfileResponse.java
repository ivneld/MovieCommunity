package Movie.MovieCommunity.web.apiDto.member;

import lombok.Data;

@Data
public class MemberProfileResponse {
    private String name;
    private String nickName;
    private String email;

    public MemberProfileResponse(String name, String nickName, String email) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
    }
}
