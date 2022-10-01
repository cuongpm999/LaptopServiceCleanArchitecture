package vn.ptit.repository.manufacturer;

import vn.ptit.model.Manufacturer;

public interface IManufacturerRepository {
    void save(Manufacturer manufacturer);
    Manufacturer getById(long id);
}
