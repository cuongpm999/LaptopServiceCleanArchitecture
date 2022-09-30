package vn.ptit.repository.manufacturer;

import org.springframework.stereotype.Repository;
import vn.ptit.model.Manufacturer;

@Repository
public class ManufacturerRepository implements IManufacturerRepository{
    private final ManufacturerJpa manufacturerJpa;

    public ManufacturerRepository(ManufacturerJpa manufacturerJpa) {
        this.manufacturerJpa = manufacturerJpa;
    }

    @Override
    public void insert(Manufacturer manufacturer) {
        ManufacturerEntity entity = ManufacturerEntity.fromDomain(manufacturer);
        manufacturerJpa.save(entity);
    }
}
