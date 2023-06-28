package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Credit;
import Movie.MovieCommunity.JPARepository.ActorRepository;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.web.apiDto.credit.CreditDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
@RequiredArgsConstructor
public class CreditService {
    private final ActorRepository actorRepository;
    @Transactional(readOnly = true)
    public CustomPageImpl<CreditDetailSearchDto> findCreditDetailSearch(String search, Pageable pageable){
        Page<Credit> credits = actorRepository.findPageByMovieNmContaining(search, pageable);
        List<CreditDetailSearchDto> creditSearch = credits.stream().map(c -> CreditDetailSearchDto.builder()
                .id(c.getId())
                .actorNm(c.getActorNm())
                .creditCategory(c.getCreditCategory())
                .profileUrl(c.getProfile_path())
                .build()
        ).collect(Collectors.toList());
        return new CustomPageImpl<>(creditSearch, pageable, credits.getTotalElements());
    }
}
