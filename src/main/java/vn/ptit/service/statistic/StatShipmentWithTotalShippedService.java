package vn.ptit.service.statistic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.Shipment;
import vn.ptit.repository.statistic.StatShipmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatShipmentWithTotalShippedService {
    private final StatShipmentRepository statShipmentRepository;

    public StatShipmentWithTotalShippedService(StatShipmentRepository statShipmentRepository) {
        this.statShipmentRepository = statShipmentRepository;
    }

    public List<Output> shipmentWithTotalShipped() {
        return statShipmentRepository.shipmentWithTotalShipped().stream().map(Output::createOutput).collect(Collectors.toList());
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
        @JsonProperty("total_shipped")
        @JsonAlias("totalShipped")
        private Integer totalShipped;

        public static StatShipmentWithTotalShippedService.Output createOutput(Shipment shipment) {
            try {
                StatShipmentWithTotalShippedService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(shipment), StatShipmentWithTotalShippedService.Output.class);
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
