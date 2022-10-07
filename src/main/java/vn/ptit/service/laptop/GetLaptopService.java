package vn.ptit.service.laptop;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.ImageLaptop;
import vn.ptit.model.Laptop;
import vn.ptit.model.Manufacturer;
import vn.ptit.model.QueryFilter;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.service.manufacturer.GetManufacturerService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetLaptopService implements IGetLaptopService {
    private final ILaptopRepository laptopRepository;

    public GetLaptopService(ILaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }


    @Override
    @SneakyThrows
    public Output getById(long id) {
        Laptop laptop = laptopRepository.findById(id);
        if (laptop == null) {
            throw new DataNotFoundException("Laptop not found");
        }
        return Output.createOutput(laptop);
    }

    @Override
    public List<Output> getList(Integer page, Integer limit) {
        QueryFilter filter = QueryFilter.create(limit,page);
        return laptopRepository.findAll(filter).stream().map(GetLaptopService.Output::createOutput).collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
        private Long id;
        private String name;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String category;
        @JsonProperty("hard_drive")
        @JsonAlias("hardDrive")
        private String hardDrive;
        private String ram;
        private String vga;
        private String cpu;
        private Double screen;
        private Double price;
        private Double discount;
        private String video;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        private String specifications;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String manufacturer;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private List<String> images;
        @JsonAlias("isDelete")
        @JsonProperty("is_delete")
        private Boolean isDelete;

        public static GetLaptopService.Output createOutput(Laptop laptop){
            try {
                GetLaptopService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(laptop), GetLaptopService.Output.class);
                output.category = laptop.getCategory().getContent();
                output.images = laptop.getImages().stream().map(ImageLaptop::getSource).collect(Collectors.toList());
                output.manufacturer = laptop.getManufacturer().getName();
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
