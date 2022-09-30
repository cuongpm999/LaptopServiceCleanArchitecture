package vn.ptit.service.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Service;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

@Service
public class CreateManufacturerService {
    private final IManufacturerRepository manufacturerRepository;

    public CreateManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public void create(CreateInput input) {
        Manufacturer manufacturer = Manufacturer.create(input.name, input.address);
        manufacturerRepository.insert(manufacturer);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CreateInput {
        public String name;
        public String address;
    }
}
