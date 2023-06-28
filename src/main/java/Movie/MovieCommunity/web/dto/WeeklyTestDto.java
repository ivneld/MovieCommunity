package Movie.MovieCommunity.web.dto;

import lombok.Data;

@Data
public class WeeklyTestDto {
    private Integer year;
    private Integer month;
    private Integer day;

    public WeeklyTestDto(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
