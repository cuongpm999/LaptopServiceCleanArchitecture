package vn.ptit.repository.manufacturer;

import vn.ptit.model.Manufacturer;
import vn.ptit.model.QueryFilter;

import java.util.List;

public interface IManufacturerRepository {
    void save(Manufacturer manufacturer);
    Manufacturer getById(long id);
    List<Manufacturer> findAll(QueryFilter filter);
}
