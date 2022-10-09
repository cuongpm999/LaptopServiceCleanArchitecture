package vn.ptit.repository.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Cart;
import vn.ptit.model.LineItem;
import vn.ptit.model.Shipment;
import vn.ptit.repository.BaseEntity;
import vn.ptit.repository.laptop.ImageLaptopEntity;
import vn.ptit.repository.shipment.ShipmentEntity;
import vn.ptit.repository.user.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    private List<LineItemEntity> lineItems = new ArrayList<>();

    public void addLineItem(LineItemEntity lineItem) {
        lineItem.setCart(this);
        this.lineItems.add(lineItem);
    }

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public CartEntity(Long id, UserEntity user, Double totalAmount, Boolean isDelete) {
        this.id = id;
        this.user = user;
        this.totalAmount = totalAmount;
        this.isDelete = isDelete;
    }

    public static CartEntity fromDomain(Cart cart) {
        CartEntity cartEntity = new CartEntity(cart.getId(), UserEntity.fromDomain(cart.getUser()), cart.getTotalAmount(),
                cart.getIsDelete());
        cart.getLineItems().forEach(lineItem -> {
            cartEntity.addLineItem(LineItemEntity.fromDomain(lineItem));
        });
        return cartEntity;
    }

    public Cart toDomain() {
        List<LineItem> lineItemList = lineItems.stream().map(LineItemEntity::toDomain).collect(Collectors.toList());
        return new Cart(id, getCreatedAt(), getUpdatedAt(), lineItemList, user.toDomain(), totalAmount, isDelete);
    }
}
