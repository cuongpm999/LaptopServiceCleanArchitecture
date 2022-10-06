package vn.ptit.service.shipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.model.Shipment;
import vn.ptit.repository.shipment.IShipmentRepository;

@Service
public class CreateShipmentService {
    private final IShipmentRepository shipmentRepository;

    public CreateShipmentService(IShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void create(CreateShipmentService.CreateInput input){
        Shipment shipment = Shipment.create(input.name, input.address, input.price);
        shipmentRepository.save(shipment);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        private String name;
        private String address;
        private Double price;
    }
}
