package Movie.MovieCommunity;

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
