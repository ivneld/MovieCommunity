package Movie.MovieCommunity;

import Movie.MovieCommunity.repository.JdbcTemplateMovieRepository;
import Movie.MovieCommunity.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//@RequiredArgsConstructor
public class JdbcTemplateConfig {
    private final DataSource dataSource;
    public JdbcTemplateConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
