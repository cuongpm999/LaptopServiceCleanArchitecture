package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private User user;
    private Shipment shipment;
    private Cart cart;
    private Payment payment;

    public Order(User user, Shipment shipment, Cart cart, Payment payment) {
        this.user = user;
        this.shipment = shipment;
        this.cart = cart;
        this.payment = payment;
        this.status = Status.PREPARE;
    }

    public enum Status {
        PREPARE(0, "Chuẩn bị hàng"),
        DELIVERY(1, "Đang giao"),
        SUCCESS(2, "Giao hàng thành công"),
        CANCEL(3, "Hủy đơn hàng");
        private int id;
        private String content;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        Status(int id, String content) {
            this.id = id;
            this.content = content;
        }

        public static Status getById(Integer number) {
            for (Status e : values()) {
                if (Objects.equals(e.id, number)) {
                    return e;
                }
            }
            return null;
        }
    }

    public static Order create(User user, Shipment shipment, Cart cart, Payment payment) {
        return new Order(user, shipment, cart, payment);
    }
}
