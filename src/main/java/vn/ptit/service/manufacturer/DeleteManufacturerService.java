package vn.ptit.service.manufacturer;

import org.springframework.stereotype.Service;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

@Service
public class DeleteManufacturerService {
    private final IManufacturerRepository manufacturerRepository;

    public DeleteManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public void delete(Long id){
        Manufacturer manufacturer = manufacturerRepository.getById(id);
        manufacturer.delete();
        manufacturerRepository.save(manufacturer);
    }
}
