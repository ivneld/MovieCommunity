package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.WeeklyBoxOffice;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateWeeklyBoxOfficeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateWeeklyBoxOfficeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("weeklyboxoffice")
                .usingGeneratedKeyColumns("id");
    }

    public WeeklyBoxOffice save(WeeklyBoxOffice weeklyBoxOffice) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(weeklyBoxOffice);
        Number key = jdbcInsert.executeAndReturnKey(param);
        weeklyBoxOffice.setId(key.longValue());
        return weeklyBoxOffice;
    }


    public int update(WeeklyBoxOffice weeklyBoxOfficeDto){
        int cnt = jdbcTemplate.update("update company set boxoffice_type = ?, show_range = ?, year_week_time = ?, rnum = ?, ranking = ?, rank_inten = ?, rank_old_and_new where id = ?",
                weeklyBoxOfficeDto.getBoxofficeType(),weeklyBoxOfficeDto.getShowRange(), weeklyBoxOfficeDto.getYearWeekTime(), weeklyBoxOfficeDto.getRnum(), weeklyBoxOfficeDto.getRanking(),weeklyBoxOfficeDto.getRankInten(),weeklyBoxOfficeDto.getRankInten(),weeklyBoxOfficeDto.getId());
        return cnt;
    }

    public Optional<WeeklyBoxOffice> findByRanking(Integer ranking) {
        String sql = "select * from weeklyboxoffice where ranking <= :ranking";
        try {
            Map<String, Object> param = Map.of("ranking", ranking);
            WeeklyBoxOffice weeklyBoxOffice = template.queryForObject(sql, param, weeklyBoxOfficeRowMapper());
            return Optional.of(weeklyBoxOffice);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public List<WeeklyBoxOffice> selectAll() {
        String sql = "select * from weeklyboxoffice";
        return template.query(sql, weeklyBoxOfficeRowMapper());
    }

    private RowMapper<WeeklyBoxOffice> weeklyBoxOfficeRowMapper() {
        return BeanPropertyRowMapper.newInstance(WeeklyBoxOffice.class);
    }
}