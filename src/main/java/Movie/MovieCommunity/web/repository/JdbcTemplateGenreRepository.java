package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.Company;
import Movie.MovieCommunity.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class JdbcTemplateGenreRepository {
    private final NamedParameterJdbcTemplate template;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateGenreRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("genre")
                .usingGeneratedKeyColumns("id");
    }
    public Genre save(Genre genre) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(genre);
        Number key = jdbcInsert.executeAndReturnKey(param);
        genre.setId(key.longValue());
        return genre;
    }

    public int update(Genre genreDto){
        int cnt = jdbcTemplate.update("update genre set genre_nm = ? where id = ?",
                genreDto.getGenreNm(), genreDto.getId());
        return cnt;
    }

    public Optional<Genre> findById(String id) {
        String sql = "select * from genre where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Genre genre = template.queryForObject(sql, param, genreRowMapper());
            return Optional.of(genre);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private RowMapper<Genre> genreRowMapper() {
        return BeanPropertyRowMapper.newInstance(Genre.class);
    }

}
