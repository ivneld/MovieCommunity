package Movie.MovieCommunity.repository;

import Movie.MovieCommunity.domain.Actor;
import Movie.MovieCommunity.domain.EtcData;
import Movie.MovieCommunity.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcTemplateMovieRepository implements MovieRepository{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate  template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateMovieRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("movie")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Movie save(Movie movie) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(movie);
        Number key = jdbcInsert.executeAndReturnKey(param);
        movie.setId(key.longValue());
        return movie;
    }

    @Override
    public Optional<Movie> findByMovieCd(String movieCd) {
        String sql = "select * from movie where movie_cd = :movie_cd";
        try {
            Map<String, Object> param = Map.of("movie_cd", movieCd);
            Movie movie = template.queryForObject(sql, param, movieRowMapper());
            return Optional.of(movie);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(Movie movieDto){
        int cnt = jdbcTemplate.update("update movie set nation_nm = ?, audit_no = ?, watch_grade_nm = ?, director_nm = ? where id = ?",
                movieDto.getNationNm(),movieDto.getAuditNo(), movieDto.getWatchGradeNm(), movieDto.getDirectorNm(), movieDto.getId());
        return cnt;
    }
    @Override
    public List<Movie> findByFilter(MovieSearchCond cond) {
        String movieNm = cond.getMovieNm();
        Integer openDt = cond.getOpenDt();

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(cond);
        String sql = "select movie_nm, show_tm, open_dt, nation_nm, director_nm, sales_acc from movie";

        if (StringUtils.hasText(movieNm) || openDt != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(movieNm)) {
            sql += " movie_nm like concat('%',:movieNm, '%')";
            andFlag = true;
        }
        if (openDt != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " open_dt <= :openDt";
        }
        log.info("sql={}", sql);

        return template.query(sql, param, movieRowMapper());
    }

    @Override
    public List<Movie> findByPageNum(List<Movie> filteredMovie, int page) {
        List<Movie> subList = filteredMovie.subList(10 * page, 10 * page + 10);
        return subList;
    }

    @Override
    public List<Movie> selectAll() {
        String sql = "select * from movie";
        return template.query(sql, movieRowMapper());
    }

    @Override
    public int setEtcData(String movieCd, EtcData etcData) {
        String sql = "update movie set sales_acc = ?, audi_acc = ?, top_score = ? where movie_cd = ?";
        int cnt = jdbcTemplate.update(sql, etcData.getSales_acc(), etcData.getAudi_acc(), etcData.getTop_score(), movieCd);
        return cnt;
    }
    private RowMapper<Movie> movieRowMapper() {
        return BeanPropertyRowMapper.newInstance(Movie.class);
    }
}
