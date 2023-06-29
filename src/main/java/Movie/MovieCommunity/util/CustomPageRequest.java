package Movie.MovieCommunity.util;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public class CustomPageRequest {
    private int page = 1;
    private int size = 10;
    private Sort.Direction direction = Sort.Direction.DESC;
    private String sortingProperty = "createdDate";

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size, direction, sortingProperty);
    }

    public PageRequest of(String sortingProperty) {
        return PageRequest.of(page - 1, size, direction, sortingProperty);
    }
    public PageRequest of(Sort.Direction direction, String sortingProperty) {
        return PageRequest.of(page - 1, size, direction, sortingProperty);
    }
}
