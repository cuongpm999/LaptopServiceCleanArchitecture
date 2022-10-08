package vn.ptit.service.laptop;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Laptop;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.laptop.ILaptopRepository;

@Service
public class DeleteLaptopService {
    private final ILaptopRepository laptopRepository;

    public DeleteLaptopService(ILaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @SneakyThrows
    public void delete(Long id){
        Laptop laptop = laptopRepository.findById(id);
        if (laptop == null) {
            throw new DataNotFoundException("Laptop not found");
        }
        laptop.delete();
        laptopRepository.save(laptop);
    }
}
