package vn.ptit.service.cart;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.*;
import vn.ptit.repository.cart.ICartRepository;
import vn.ptit.service.laptop.GetLaptopService;
import vn.ptit.service.user.GetUserService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindCurrentCartService {
    private final ICartRepository cartRepository;

    public FindCurrentCartService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Output findCurrentCart(String username) {
        Cart cart = cartRepository.findCurrentCart(username);
        if (cart != null) {
            return Output.createOutput(cart);
        }
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
        private Long id;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        private List<LineItemOutput> lineItems;
        private UserOutput user;
        @JsonAlias("totalAmount")
        @JsonProperty("total_amount")
        private Double totalAmount;
        @JsonAlias("isDelete")
        @JsonProperty("is_delete")
        private Boolean isDelete;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class LineItemOutput {
            private Long id;
            private Integer quantity;
            private Double discount;
            private Double price;
            private LaptopOutput laptop;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @Data
            public static class LaptopOutput {
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
                private ManufacturerOutput manufacturer;
                @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                private List<String> images;

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

                public static LaptopOutput createOutput(Laptop laptop) {
                    try {
                        LaptopOutput output = MyObjectMapper.get()
                                .readValue(MyObjectMapper.get().writeValueAsString(laptop), LaptopOutput.class);
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

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class UserOutput {
            @JsonAlias("id")
            private Long id;
            @JsonProperty("full_name")
            @JsonAlias("fullName")
            private String fullName;
            @JsonAlias("address")
            private String address;
            @JsonAlias("email")
            private String email;
            @JsonAlias("mobile")
            private String mobile;
            @JsonAlias("sex")
            private Boolean sex;
            @JsonProperty("date_of_birth")
            @JsonAlias("dateOfBirth")
            private Date dateOfBirth;
            @JsonAlias("username")
            private String username;
            @JsonAlias("position")
            private String position;
            @JsonAlias("avatar")
            private String avatar;
        }

        public static Output createOutput(Cart cart) {
            try {
                Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(cart), Output.class);
                output.lineItems.forEach(l -> {

                });
                for (int i = 0; i < output.lineItems.size(); i++) {
                    output.lineItems.get(i).laptop = LineItemOutput.LaptopOutput.createOutput(cart.getLineItems().get(i).getLaptop());
                }
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
