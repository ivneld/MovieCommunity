package Movie.MovieCommunity.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = {"pageable", "last", "totalPages", "totalElements", "size", "number", "first", "numberOfElements", "sort", "empty"})
public class CustomPageImpl<T> extends PageImpl<T> {

    @JsonCreator
    public CustomPageImpl(List content, Pageable pageable, long total) {
        super(content,pageable.getSort().isSorted() ? pageable : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending()), total);
    }

    @JsonCreator
    public CustomPageImpl(List content) {
        super(content);
    }

    @JsonGetter(value = "contents")
    @Override
    public List getContent() {
        return super.getContent();
    }

    @JsonGetter(value = "paging")
    public Map getPaging() {
        Map<String, Object> paging = new HashMap<>();
        paging.put("totalPages", super.getTotalPages());
        paging.put("totalElements", super.getTotalElements());
        paging.put("pageSize", super.getSize());
        paging.put("pageNumber", super.getNumber() + 1);
        paging.put("isFirst", super.isFirst());
        paging.put("isLast", super.isLast());
        paging.put("sort", super.getSort());
        paging.put("isEmpty", super.isEmpty());
        return paging;
    }
}