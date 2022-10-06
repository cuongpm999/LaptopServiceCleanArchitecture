package vn.ptit.service.shipment;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Shipment;
import vn.ptit.repository.shipment.IShipmentRepository;

@Service
public class DeleteShipmentService {
    private final IShipmentRepository shipmentRepository;

    public DeleteShipmentService(IShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }
    @SneakyThrows
    public void delete(Long id){
        Shipment shipment = shipmentRepository.getById(id);
        if(shipment == null){
            throw new DataNotFoundException("Shipment not found");
        }
        shipment.delete();
        shipmentRepository.save(shipment);
    }
}
