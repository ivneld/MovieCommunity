package Movie.MovieCommunity.config;

import Movie.MovieCommunity.JPADomain.CreditCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, CreditCategory> {
    @Override
    public CreditCategory convert(String source) {
        System.out.println("source = " + source);
        return CreditCategory.valueOf(source.toUpperCase());
    }
}