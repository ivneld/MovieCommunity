package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.MovieWithCompany;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class JdbcTemplateMovieWithCompanyRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateMovieWithCompanyRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("moviewithcompany")
                .usingGeneratedKeyColumns("id");
    }
    public MovieWithCompany save(MovieWithCompany movieWithCompany ) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(movieWithCompany);
        Number key = jdbcInsert.executeAndReturnKey(param);
        movieWithCompany.setId(key.longValue());
        return movieWithCompany;
    }
}
