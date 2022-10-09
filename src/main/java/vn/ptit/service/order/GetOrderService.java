package vn.ptit.service.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.*;
import vn.ptit.repository.order.IOrderRepository;
import vn.ptit.service.laptop.GetLaptopService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOrderService implements IGetOrderService {
    private static final Integer CASH = 1;
    private static final Integer DIGITAL_WALLET = 2;
    private final IOrderRepository orderRepository;

    public GetOrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Output> getListOrderByUser(String username, Integer page, Integer limit) {
        QueryFilter filter = QueryFilter.create(limit, page);
        return orderRepository.findByUser(username, filter).stream().map(GetOrderService.Output::createOutput).collect(Collectors.toList());
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

        private UserOutput user;
        private ShipmentOutput shipment;
        private CartOutput cart;
        private PaymentOutput payment;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String status;

        public static class CartOutput {
            private Long id;
            @JsonAlias("lineItems")
            @JsonProperty("line_items")
            private List<LineItemOutput> lineItems;
            @JsonAlias("totalAmount")
            @JsonProperty("total_amount")
            private Double totalAmount;

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

            public static CartOutput createOutput(Cart cart) {
                try {
                    CartOutput output = MyObjectMapper.get()
                            .readValue(MyObjectMapper.get().writeValueAsString(cart), CartOutput.class);
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

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class ShipmentOutput {
            private Long id;
            private String name;
            private String address;
            private Double price;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class PaymentOutput {
            private Long id;
            @JsonAlias("total_money")
            private Double totalMoney;
            private String name;
            @JsonAlias("cash_tendered")
            private Double cashTendered;
            private Integer type;

            public static PaymentOutput createOutput(Payment payment) {
                try {
                    PaymentOutput output = MyObjectMapper.get()
                            .readValue(MyObjectMapper.get().writeValueAsString(payment), PaymentOutput.class);
                    if(payment instanceof Cash) output.type = CASH;
                    else if(payment instanceof DigitalWallet) output.type = DIGITAL_WALLET;
                    return output;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        public static Output createOutput(Order order) {
            try {
                Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(order), Output.class);
                output.status = order.getStatus().getContent();
                output.cart = CartOutput.createOutput(order.getCart());
                output.payment = PaymentOutput.createOutput(order.getPayment());
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
