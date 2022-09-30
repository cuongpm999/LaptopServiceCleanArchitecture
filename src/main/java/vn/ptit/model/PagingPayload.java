package vn.ptit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class PagingPayload<T> {
    private List<T> data;
    private Long timestamp;

    public static PagingPayload empty() {
        return PagingPayload.builder().data(Collections.emptyList()).build();
    }
}
