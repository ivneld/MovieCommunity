package Movie.MovieCommunity.repository;

import Movie.MovieCommunity.domain.Actor;
import Movie.MovieCommunity.domain.Movie;
import Movie.MovieCommunity.domain.MovieWithActor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateMovieWithActorRepositoryTest {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    JdbcTemplateActorRepository actorRepository;

    public JdbcTemplateMovieWithActorRepositoryTest(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("moviewithactor")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    public List<Actor> findActorByMovie(Movie movie) {
        String sql = "select * from moviewithactor where movie_id=:id";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(movie);
        List<MovieWithActor> movieWithActors = template.query(sql, param, movieWithActorRowMapper());

        List<Actor> actorList = new ArrayList<>();
        for (int i = 0; i < movieWithActors.size(); i++) {
            Actor actor = actorRepository.findById(movieWithActors.get(i).getActorId().toString()).get();
            actorList.add(actor);
        }

        return actorList;
    }

    private RowMapper<MovieWithActor> movieWithActorRowMapper() {
        return BeanPropertyRowMapper.newInstance(MovieWithActor.class);
    }
}