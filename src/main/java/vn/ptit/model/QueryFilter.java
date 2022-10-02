package vn.ptit.model;

import lombok.Getter;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

@Getter
public class QueryFilter {
    private Integer limit;

    private Integer page;

    public QueryFilter(Integer limit, Integer page) {
        this.limit = limit;
        this.page = page;
    }

    @SneakyThrows
    public static QueryFilter create(Integer limit, Integer page) {
        if (page != null && page < 0) {
            throw new InvalidDataException("QueryFilter[page] must >= 0");
        }

        if (limit != null && limit < 0) {
            throw new InvalidDataException("QueryFilter[limit] must >= 0");
        }
        return new QueryFilter(limit, page);
    }
}
