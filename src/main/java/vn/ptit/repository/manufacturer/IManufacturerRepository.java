package vn.ptit.repository.manufacturer;

import vn.ptit.model.Manufacturer;

import java.util.List;

public interface IManufacturerRepository {
    void save(Manufacturer manufacturer);
    Manufacturer getById(long id);
    List<Manufacturer> findAll();
}
