package vn.ptit.service.shipment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.QueryFilter;
import vn.ptit.model.Shipment;
import vn.ptit.repository.shipment.IShipmentRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetShipmentService implements IGetShipmentService{
    private final IShipmentRepository shipmentRepository;

    public GetShipmentService(IShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @SneakyThrows
    @Override
    public Output getById(long id) {
        Shipment shipment = shipmentRepository.getById(id);
        if(shipment == null){
            throw new DataNotFoundException("Shipment not found");
        }
        return GetShipmentService.Output.createOutput(shipment);
    }

    @Override
    public List<Output> getList(Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit,page, sort);
        return shipmentRepository.getAll(filter).stream().map(Output::createOutput).collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
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

        public static GetShipmentService.Output createOutput(Shipment shipment){
            try {
                GetShipmentService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(shipment), GetShipmentService.Output.class);
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
