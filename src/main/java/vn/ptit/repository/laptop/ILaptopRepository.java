package vn.ptit.repository.laptop;

import vn.ptit.model.Laptop;

public interface ILaptopRepository {
    void save(Laptop laptop);
    Laptop findById(long id);
}
