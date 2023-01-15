package vn.ptit.service.shipment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.Shipment;

import java.util.Date;
import java.util.List;

public interface IGetShipmentService {
    Output getById(long id);
    List<Output> getList(Integer page, Integer limit, String sort);

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    class Output {
        @JsonAlias("id")
        private Long id;
        @JsonAlias("name")
        private String name;
        @JsonAlias("address")
        private String address;
        @JsonAlias("price")
        private Double price;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        @JsonProperty("is_delete")
        @JsonAlias("isDelete")
        private Boolean isDelete;

        public static Output createOutput(Shipment shipment){
            try {
                return MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(shipment), Output.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
