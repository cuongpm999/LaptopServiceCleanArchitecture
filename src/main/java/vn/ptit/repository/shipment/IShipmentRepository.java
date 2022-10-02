package vn.ptit.repository.shipment;

import vn.ptit.model.QueryFilter;
import vn.ptit.model.Shipment;

import java.util.List;

public interface IShipmentRepository {
    void save(Shipment shipment);
    Shipment getById(long id);
    List<Shipment> getAll(QueryFilter filter);
}
