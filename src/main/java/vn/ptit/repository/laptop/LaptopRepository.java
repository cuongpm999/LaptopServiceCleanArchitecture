package vn.ptit.repository.laptop;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.model.Laptop;
import vn.ptit.model.QueryFilter;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LaptopRepository implements ILaptopRepository{
    private final LaptopJpa laptopJpa;
    private final ImageJpa imageJpa;

    public LaptopRepository(LaptopJpa laptopJpa, ImageJpa imageJpa) {
        this.laptopJpa = laptopJpa;
        this.imageJpa = imageJpa;
    }

    @Override
    public void save(Laptop laptop) {
        laptopJpa.save(LaptopEntity.fromDomain(laptop));
    }

    @Override
    public Laptop findById(long id) {
        LaptopEntity laptopEntity = laptopJpa.findByIsDeleteFalseAndId(id);
        if(laptopEntity == null)
            return null;
        return laptopEntity.toDomain();
    }

    @Override
    public List<Laptop> findAll(QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit());
        return laptopJpa.findByIsDeleteFalse(pageable).stream().map(LaptopEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(Laptop laptop) {
        imageJpa.deleteImageByLaptopId(laptop.getId());
        save(laptop);
    }
}
