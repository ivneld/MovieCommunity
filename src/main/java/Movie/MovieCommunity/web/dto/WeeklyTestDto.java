package Movie.MovieCommunity.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WeeklyTestDto {
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;
    @NotNull
    private Integer day;

    public WeeklyTestDto(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
