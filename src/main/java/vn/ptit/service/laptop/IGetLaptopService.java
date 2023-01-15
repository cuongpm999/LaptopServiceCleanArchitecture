package vn.ptit.service.laptop;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.ImageLaptop;
import vn.ptit.model.Laptop;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface IGetLaptopService {
    Output getById(long id);
    List<Output> getList(Integer page, Integer limit, String sort);
    List<Output> getSameManufacturer(Long manufacturerId, Long id, Integer limit);
    List<Output> filter(Integer page, Integer limit, String sort,
                                         String searchText, List<Long> manufacturerIds,
                                         List<Integer> categories, List<String> cpus,
                                         List<String> rams, List<String> hardDrives, List<String> vgas);

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
        private ManufacturerOutput manufacturer;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private List<String> images;
        @JsonAlias("isDelete")
        @JsonProperty("is_delete")
        private Boolean isDelete;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class ManufacturerOutput {
            @JsonAlias("id")
            private Long id;
            @JsonAlias("name")
            private String name;
            @JsonAlias("address")
            private String address;
        }

        public static Output createOutput(Laptop laptop){
            try {
                Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(laptop), Output.class);
                output.category = laptop.getCategory().getContent();
                output.images = laptop.getImages().stream().map(ImageLaptop::getSource).collect(Collectors.toList());
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
