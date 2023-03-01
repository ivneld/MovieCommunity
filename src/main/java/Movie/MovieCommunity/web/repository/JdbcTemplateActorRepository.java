package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.Actor;
import Movie.MovieCommunity.domain.Movie;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class JdbcTemplateActorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateActorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("actor")
                .usingGeneratedKeyColumns("id");
    }
    public Actor save(Actor actor) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(actor);
        Number key = jdbcInsert.executeAndReturnKey(param);
        actor.setId(key.longValue());
        return actor;
    }


    public Optional<Actor> findById(String id) {
        String sql = "select * from actor where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Actor actor = template.queryForObject(sql, param, actorRowMapper());
            return Optional.of(actor);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    public int update(Actor actorDto){
        int cnt = jdbcTemplate.update("update actor set actor_nm = ? where id = ?",
                actorDto.getActorNm(), actorDto.getId());
        return cnt;
    }


    public void increaseCnt(Actor actor) {
        jdbcTemplate.update("update actor set top_movie_cnt = top_movie_cnt + 1 where id = ?",
                actor.getId());
    }

    private RowMapper<Actor> actorRowMapper() {
        return BeanPropertyRowMapper.newInstance(Actor.class);
    }

}
