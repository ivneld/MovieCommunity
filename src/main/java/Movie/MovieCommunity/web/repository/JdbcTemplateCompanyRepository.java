package Movie.MovieCommunity.web.repository;

import Movie.MovieCommunity.domain.Company;
import Movie.MovieCommunity.domain.Movie;
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
public class JdbcTemplateCompanyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateCompanyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("company")
                .usingGeneratedKeyColumns("id");
    }
    public Company save(Company company) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(company);
        Number key = jdbcInsert.executeAndReturnKey(param);
        company.setId(key.longValue());
        return company;
    }
    public int update(Company companyDto){
        int cnt = jdbcTemplate.update("update company set company_cd = ?, company_nm = ?, company_nm_en = ?, company_part_nm = ? where id = ?",
                companyDto.getCompanyCd(),companyDto.getCompanyNm(), companyDto.getCompanyNmEn(), companyDto.getCompanyPartNm(), companyDto.getId());
        return cnt;
    }


    public Optional<Company> findById(String id) {
        String sql = "select * from company where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Company company = template.queryForObject(sql, param, companyRowMapper());
            return Optional.of(company);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Company> companyRowMapper() {
        return BeanPropertyRowMapper.newInstance(Company.class);
    }

}
