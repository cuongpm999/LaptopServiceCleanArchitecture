package vn.ptit.service.shipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Shipment;
import vn.ptit.repository.shipment.IShipmentRepository;

@Service
public class UpdateShipmentService {
    private final IShipmentRepository shipmentRepository;

    public UpdateShipmentService(IShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @SneakyThrows
    public void update(UpdateShipmentService.UpdateInput input){
        Shipment shipment = shipmentRepository.getById(input.id);
        if(shipment == null){
            throw new DataNotFoundException("Shipment not found");
        }
        shipment.update(input.name, input.address, input.price);
        shipmentRepository.save(shipment);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class UpdateInput {
        public Long id;
        private String name;
        private String address;
        private Double price;
    }
}
