package vn.ptit.repository.laptop;

import org.springframework.stereotype.Repository;
import vn.ptit.model.Laptop;

@Repository
public class LaptopRepository implements ILaptopRepository{
    private final LaptopJpa laptopJpa;

    public LaptopRepository(LaptopJpa laptopJpa) {
        this.laptopJpa = laptopJpa;
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
}
