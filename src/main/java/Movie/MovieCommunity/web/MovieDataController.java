package Movie.MovieCommunity.web;

import Movie.MovieCommunity.dataCollection.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/movie/set")
@RestController
@RequiredArgsConstructor
public class MovieDataController {
    private final MovieDataService movieDataService;
    @GetMapping
    public ResponseEntity setMovie() throws Exception {
        movieDataService.Testing();
        return new ResponseEntity(HttpStatus.OK);
    }
}
