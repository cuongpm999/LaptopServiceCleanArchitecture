package vn.ptit.service.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.exception.InvalidDataException;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

@Service
public class UpdateManufacturerService {
    private final IManufacturerRepository manufacturerRepository;

    public UpdateManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @SneakyThrows
    public void update(UpdateInput input) {
        Manufacturer manufacturer = manufacturerRepository.getById(input.id);
        if (manufacturer == null) {
            throw new DataNotFoundException("Manufacturer not found");
        }
        manufacturer.update(input.name, input.address);
        manufacturerRepository.save(manufacturer);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpdateInput {
        public Long id;
        public String name;
        public String address;
    }
}
