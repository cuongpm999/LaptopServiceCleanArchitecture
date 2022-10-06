package vn.ptit.service.manufacturer;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.Manufacturer;
import vn.ptit.model.QueryFilter;
import vn.ptit.repository.manufacturer.IManufacturerRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetManufacturerService {
    private final IManufacturerRepository manufacturerRepository;

    public GetManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Output> getList(Integer page, Integer limit){
        QueryFilter filter = QueryFilter.create(limit,page);
        return manufacturerRepository.findAll(filter).stream().map(Output::createOutput).collect(Collectors.toList());
    }

    @SneakyThrows
    public Output get(long id){
        Manufacturer manufacturer = manufacturerRepository.getById(id);
        if (manufacturer == null) {
            throw new DataNotFoundException("Manufacturer not found");
        }
        return Output.createOutput(manufacturer);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
        @JsonAlias("id")
        private Long id;
        @JsonAlias("name")
        private String name;
        @JsonAlias("address")
        private String address;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        @JsonAlias("isDelete")
        @JsonProperty("is_delete")
        private Boolean isDelete;

        public static Output createOutput(Manufacturer manufacturer){
            try {
                Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(manufacturer), Output.class);
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
