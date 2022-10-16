package vn.ptit.service.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.*;
import vn.ptit.repository.cart.ICartRepository;
import vn.ptit.repository.order.IOrderRepository;
import vn.ptit.repository.payment.IPaymentRepository;
import vn.ptit.repository.shipment.IShipmentRepository;
import vn.ptit.repository.user.IUserRepository;
import vn.ptit.service.mail.SendMailService;

@Service
public class CreateOrderService {
    private static final Integer CASH = 1;
    private static final Integer DIGITAL_WALLET = 2;

    private final IUserRepository userRepository;
    private final IShipmentRepository shipmentRepository;
    private final ICartRepository cartRepository;
    private final IPaymentRepository paymentRepository;
    private final IOrderRepository orderRepository;
    private final SendMailService sendMailService;

    public CreateOrderService(IUserRepository userRepository,
                              IShipmentRepository shipmentRepository,
                              ICartRepository cartRepository,
                              IPaymentRepository paymentRepository,
                              IOrderRepository orderRepository,
                              SendMailService sendMailService) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.cartRepository = cartRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.sendMailService = sendMailService;
    }

    @SneakyThrows
    @Transactional
    public void create(CreateInput input) {
        Shipment shipment = shipmentRepository.getById(input.shipmentId);
        if (shipment == null) {
            throw new DataNotFoundException("Shipment not found");
        }
        User user = userRepository.getById(input.userId);
        if (user == null) {
            throw new DataNotFoundException("User not found");
        }
        Cart cart = cartRepository.getById(input.cartId);
        if (cart == null) {
            throw new DataNotFoundException("Cart not found");
        }
        cart.deleteAll();

        if (input.payment.type == CASH) {
            Cash cash = Cash.create(input.payment.totalMoney, input.payment.cashTendered);
            cash = paymentRepository.saveCash(cash);
            Order order = Order.create(user, shipment, cart, cash);

            order = orderRepository.save(order);
            cartRepository.save(cart);

            sendMailService.sendMail(order);
        } else if (input.payment.type == DIGITAL_WALLET) {
            DigitalWallet digitalWallet = DigitalWallet.create(input.payment.totalMoney, input.payment.name);
            digitalWallet = paymentRepository.saveDigitalWallet(digitalWallet);
            Order order = Order.create(user, shipment, cart, digitalWallet);

            order = orderRepository.save(order);
            cartRepository.save(cart);

            sendMailService.sendMail(order);
        }



    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        @JsonAlias("user_id")
        private Long userId;
        @JsonAlias("shipment_id")
        private Long shipmentId;
        @JsonAlias("cart_id")
        private Long cartId;
        private PaymentInput payment;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class PaymentInput {
            @JsonAlias("total_money")
            private Double totalMoney;
            private String name;
            @JsonAlias("cash_tendered")
            private Double cashTendered;
            private Integer type;
        }
    }
}
