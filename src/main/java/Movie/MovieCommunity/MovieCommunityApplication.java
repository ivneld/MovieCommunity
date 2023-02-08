package Movie.MovieCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class MovieCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCommunityApplication.class, args);
	}

//	@Bean
//	public AuditorAware<String> auditorProvider(HttpServletRequest request){
//		HttpSession session = request.getSession();
////		session.getAttribute()
//		return () -> Optional.of(UUID.randomUUID().toString());
//	}
}
