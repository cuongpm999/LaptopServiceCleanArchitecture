package vn.ptit.service.statistic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.ImageLaptop;
import vn.ptit.model.Laptop;
import vn.ptit.model.QueryFilter;
import vn.ptit.repository.statistic.StatLaptopRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatLaptopWithTotalSoldService {
    private final StatLaptopRepository statLaptopRepository;

    public StatLaptopWithTotalSoldService(StatLaptopRepository statLaptopRepository) {
        this.statLaptopRepository = statLaptopRepository;
    }

    public List<Output> laptopWithTotalSold(Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return statLaptopRepository.laptopWithTotalSold(filter).stream().map(Output::createOutput).collect(Collectors.toList());
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
        private String specifications;
        @JsonProperty("total_sold")
        @JsonAlias("totalSold")
        private Integer totalSold;

        public static StatLaptopWithTotalSoldService.Output createOutput(Laptop laptop){
            try {
                StatLaptopWithTotalSoldService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(laptop), StatLaptopWithTotalSoldService.Output.class);
                output.category = laptop.getCategory().getContent();
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
