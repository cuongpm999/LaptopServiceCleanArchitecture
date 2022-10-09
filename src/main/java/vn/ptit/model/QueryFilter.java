package vn.ptit.model;

import lombok.Getter;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

import java.util.Collection;

@Getter
public class QueryFilter {
    private Integer limit;

    private Integer page;
    private String sort;

    public QueryFilter(Integer limit, Integer page) {
        this.limit = limit;
        this.page = page;
    }

    public QueryFilter(Integer limit, Integer page, String sort) {
        this.limit = limit;
        this.page = page;
        this.sort = sort;
    }

    @SneakyThrows
    public static QueryFilter create(Integer limit, Integer page, String sort) {
        if (page != null && page < 0) {
            throw new InvalidDataException("QueryFilter[page] must >= 0");
        }

        if (limit != null && limit < 0) {
            throw new InvalidDataException("QueryFilter[limit] must >= 0");
        }

        if(!(sort.equals("asc") || sort.equals("desc"))){
            throw new InvalidDataException("QueryFilter[sort] must 'asc' or 'desc'");
        }
        return new QueryFilter(limit, page, sort);
    }
}
