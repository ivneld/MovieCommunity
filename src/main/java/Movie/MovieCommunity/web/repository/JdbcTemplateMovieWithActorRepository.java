package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.Actor;
import Movie.MovieCommunity.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class JdbcTemplateMovieWithActorRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    JdbcTemplateActorRepository actorRepository;

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

    /**
     * movie -> actors
     * @param movie
     * @return
     */
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
