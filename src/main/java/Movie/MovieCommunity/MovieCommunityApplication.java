package Movie.MovieCommunity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(servers = {@Server(url = "https://moive.store", description = "Default Server URL")})
public class MovieCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCommunityApplication.class, args);
	}



}
