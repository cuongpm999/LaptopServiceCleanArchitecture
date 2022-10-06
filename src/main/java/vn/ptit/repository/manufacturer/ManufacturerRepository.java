package vn.ptit.repository.manufacturer;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.ptit.model.Manufacturer;
import vn.ptit.model.QueryFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ManufacturerEntity manufacturerEntity = manufacturerJpa.findByIdAndIsDeleteFalse(id);
        if(manufacturerEntity == null) return null;
        return manufacturerEntity.toDomain();
    }

    @Override
    public List<Manufacturer> findAll(QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit());
        return manufacturerJpa.findByIsDeleteFalse(pageable).stream().map(ManufacturerEntity::toDomain).collect(Collectors.toList());
    }
}
