package vn.ptit.repository.manufacturer;

import org.springframework.stereotype.Repository;
import vn.ptit.model.Manufacturer;

import java.util.Optional;

@Repository
public class ManufacturerRepository implements IManufacturerRepository{
    private final ManufacturerJpa manufacturerJpa;

    public ManufacturerRepository(ManufacturerJpa manufacturerJpa) {
        this.manufacturerJpa = manufacturerJpa;
    }

    @Override
    public void save(Manufacturer manufacturer) {
        ManufacturerEntity entity = ManufacturerEntity.fromDomain(manufacturer);
        manufacturerJpa.save(entity);
    }

    @Override
    public Manufacturer getById(long id) {
        Optional<ManufacturerEntity> optionalManufacturer = manufacturerJpa.findById(id);
        if(optionalManufacturer.isPresent()){
            Manufacturer manufacturer = optionalManufacturer.get().toDomain();
            return manufacturer;
        }
        return null;
    }
}
