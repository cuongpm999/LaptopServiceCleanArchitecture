package vn.ptit.service.manufacturer;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

@Service
public class DeleteManufacturerService {
    private final IManufacturerRepository manufacturerRepository;

    public DeleteManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }
    @SneakyThrows
    public void delete(Long id){
        Manufacturer manufacturer = manufacturerRepository.getById(id);
        if (manufacturer == null) {
            throw new DataNotFoundException("Manufacturer not found");
        }
        manufacturer.delete();
        manufacturerRepository.save(manufacturer);
    }
}
