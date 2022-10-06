package vn.ptit.repository.shipment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.ptit.model.QueryFilter;
import vn.ptit.model.Shipment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ShipmentRepository implements IShipmentRepository{
    private final ShipmentJpa shipmentJpa;

    public ShipmentRepository(ShipmentJpa shipmentJpa) {
        this.shipmentJpa = shipmentJpa;
    }

    @Override
    public void save(Shipment shipment) {
        ShipmentEntity shipmentEntity = ShipmentEntity.fromDomain(shipment);
        shipmentJpa.save(shipmentEntity);
    }

    @Override
    public Shipment getById(long id) {
        Optional<ShipmentEntity> optionalShipment = shipmentJpa.findById(id);
        if(optionalShipment.isPresent()){
            Shipment shipment = optionalShipment.get().toDomain();
            return shipment;
        }
        return null;
    }

    @Override
    public List<Shipment> getAll(QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit());
        return shipmentJpa.findByIsDeleteFalse(pageable).stream().map(ShipmentEntity::toDomain).collect(Collectors.toList());
    }
}
