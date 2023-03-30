package Movie.MovieCommunity;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.web.SessionConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class MovieCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCommunityApplication.class, args);
	}



}
