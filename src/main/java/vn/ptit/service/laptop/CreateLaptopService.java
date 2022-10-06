package vn.ptit.service.laptop;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Laptop;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.repository.manufacturer.IManufacturerRepository;
import vn.ptit.repository.manufacturer.ManufacturerEntity;

import java.util.List;

@Service
public class CreateLaptopService {
    private final ILaptopRepository laptopRepository;
    private final IManufacturerRepository manufacturerRepository;

    public CreateLaptopService(ILaptopRepository laptopRepository,
                               IManufacturerRepository manufacturerRepository) {
        this.laptopRepository = laptopRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @SneakyThrows
    public void create(CreateInput input){
        Laptop laptop = Laptop.create(input.name,input.category,input.hardDrive,input.ram,input.vga,input.cpu,input.screen,
                input.price,input.discount,input.video,input.specifications,input.images);
        Manufacturer manufacturer = manufacturerRepository.getById(input.manufacturer);
        if (manufacturer == null) {
            throw new DataNotFoundException("Manufacturer not found");
        }
        laptop.setManufacturer(manufacturer);
        laptopRepository.save(laptop);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        private String name;
        private Integer category;
        @JsonAlias("hard_drive")
        private String hardDrive;
        private String ram;
        private String vga;
        private String cpu;
        private Double screen;
        private Double price;
        private Double discount;
        private String video;
        private String specifications;
        private Integer manufacturer;
        private List<String> images;
    }
}
