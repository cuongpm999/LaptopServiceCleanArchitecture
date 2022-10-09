package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.repository.cart.CartEntity;
import vn.ptit.repository.user.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private Long id;
    private Laptop laptop;
    private Integer quantity;
    private Double discount;
    private Double price;

    public LineItem(Laptop laptop, Integer quantity, Double discount, Double price) {
        this.laptop = laptop;
        this.quantity = quantity;
        this.discount = discount;
        this.price = price;
    }
}
