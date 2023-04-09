package Movie.MovieCommunity.web;

import Movie.MovieCommunity.jwt.TokenDto;
import Movie.MovieCommunity.service.AuthService;
import Movie.MovieCommunity.web.dto.MemberRequestDto;
import Movie.MovieCommunity.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto);
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    //@ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/checkIdDuplicate")
    public ResponseEntity checkId(@RequestBody Map<String,Object> requestMap){
        System.out.println("email = " + requestMap.get("email").toString());
        if( authService.checkId(requestMap.get("email").toString())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
          }
        else{
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
