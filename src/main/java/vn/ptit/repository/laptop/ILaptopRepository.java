package vn.ptit.repository.laptop;

import vn.ptit.model.Laptop;
import vn.ptit.model.LaptopFilter;
import vn.ptit.model.QueryFilter;

import java.util.List;

public interface ILaptopRepository {
    void save(Laptop laptop);
    Laptop findById(long id);
    List<Laptop> findAll(QueryFilter filter);
    void update(Laptop laptop);
    List<Laptop> findSameManufacturer(long manufacturerId, long id, int limit);
    List<Laptop> filter(LaptopFilter laptopFilter);
}
