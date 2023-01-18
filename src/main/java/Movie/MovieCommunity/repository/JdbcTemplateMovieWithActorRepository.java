package Movie.MovieCommunity.repository;

import Movie.MovieCommunity.domain.Actor;
import Movie.MovieCommunity.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMovieWithActorRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateMovieWithActorRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("moviewithactor")
                .usingGeneratedKeyColumns("id");
    }
    public MovieWithActor save(MovieWithActor movieWithActor ) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(movieWithActor);
        Number key = jdbcInsert.executeAndReturnKey(param);
        movieWithActor.setId(key.longValue());
        return movieWithActor;
    }

}
