package Movie.MovieCommunity.web;

import Movie.MovieCommunity.service.CreditService;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.util.CustomPageRequest;
import Movie.MovieCommunity.web.apiDto.credit.CreditDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name="credit", description="크레딧 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;
    @Operation(method = "get", summary = "크레딧 상세 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "크레딧 상세 검색 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/search/creditdetail")
    public ResponseEntity<CustomPageImpl<CreditDetailSearchDto>> movieDetailSearch(CustomPageRequest pageRequest, @RequestParam String search){
        PageRequest of = pageRequest.of("id");
        Pageable pageable = (Pageable) of;
        CustomPageImpl<CreditDetailSearchDto> response = creditService.findCreditDetailSearch(search, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
