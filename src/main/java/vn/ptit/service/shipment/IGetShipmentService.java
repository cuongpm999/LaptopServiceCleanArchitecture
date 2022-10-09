package vn.ptit.service.shipment;

import java.util.List;

public interface IGetShipmentService {
    GetShipmentService.Output getById(long id);
    List<GetShipmentService.Output> getList(Integer page, Integer limit, String sort);
}
