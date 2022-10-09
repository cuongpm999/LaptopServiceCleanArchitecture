package vn.ptit.repository.cart;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptit.model.Cart;
import vn.ptit.model.ImageLaptop;
import vn.ptit.model.LineItem;
import vn.ptit.repository.laptop.LaptopEntity;
import vn.ptit.repository.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "line_items")
@Data
@NoArgsConstructor
public class LineItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "discount", nullable = false)
    private Double discount;
    @Column(name = "price", nullable = false)
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laptop_id", nullable = false)
    private LaptopEntity laptop;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    public LineItemEntity(Long id, Integer quantity, Double discount, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.discount = discount;
        this.price = price;
    }

    public static LineItemEntity fromDomain(LineItem lineItem) {
        LineItemEntity lineItemEntity = new LineItemEntity(lineItem.getId(), lineItem.getQuantity(), lineItem.getDiscount(), lineItem.getPrice());

        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setId(lineItem.getLaptop().getId());
        lineItemEntity.setLaptop(laptopEntity);

        return lineItemEntity;
    }

    public LineItem toDomain() {
        return new LineItem(id, laptop.toDomain(), quantity, discount, price);
    }
}
