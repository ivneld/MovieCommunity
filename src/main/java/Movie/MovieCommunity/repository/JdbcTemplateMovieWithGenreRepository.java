package Movie.MovieCommunity.repository;

import Movie.MovieCommunity.domain.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateMovieWithGenreRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateMovieWithGenreRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("moviewithgenre")
                .usingGeneratedKeyColumns("id");
    }
    public MovieWithGenre save(MovieWithGenre movieWithGenre ) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(movieWithGenre);
        Number key = jdbcInsert.executeAndReturnKey(param);
        movieWithGenre.setId(key.longValue());
        return movieWithGenre;
    }
}
