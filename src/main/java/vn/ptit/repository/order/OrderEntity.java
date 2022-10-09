package vn.ptit.repository.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Cash;
import vn.ptit.model.DigitalWallet;
import vn.ptit.model.Order;
import vn.ptit.repository.BaseEntity;
import vn.ptit.repository.cart.CartEntity;
import vn.ptit.repository.payment.CashEntity;
import vn.ptit.repository.payment.DigitalWalletEntity;
import vn.ptit.repository.payment.PaymentEntity;
import vn.ptit.repository.shipment.ShipmentEntity;
import vn.ptit.repository.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_id", nullable = false)
    private ShipmentEntity shipment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity payment;

    public static OrderEntity fromDomain(Order order) {
        if (order.getPayment() instanceof Cash) {
            return new OrderEntity(order.getId(), order.getStatus().getId(), UserEntity.fromDomain(order.getUser()),
                    ShipmentEntity.fromDomain(order.getShipment()), CartEntity.fromDomain(order.getCart()),
                    CashEntity.fromDomain((Cash) order.getPayment()));
        } else {
            return new OrderEntity(order.getId(), order.getStatus().getId(), UserEntity.fromDomain(order.getUser()),
                    ShipmentEntity.fromDomain(order.getShipment()), CartEntity.fromDomain(order.getCart()),
                    DigitalWalletEntity.fromDomain((DigitalWallet) order.getPayment()));
        }
    }

    public Order toDomain() {
        return new Order(id, getCreatedAt(), getUpdatedAt(), Order.Status.getById(status), user.toDomain(),
                shipment.toDomain(), cart.toDomain(), payment.toDomain());
    }
}
