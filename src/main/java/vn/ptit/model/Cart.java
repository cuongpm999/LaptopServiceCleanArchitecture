package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.exception.DataNotFoundException;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private List<LineItem> lineItems;
    private User user;
    private Double totalAmount;
    private Boolean isDelete;


    public Cart(User user, Double totalAmount) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.lineItems = new ArrayList<>();
    }

    public static Cart create(User user) {
        return new Cart(user, 0.0);
    }

    public void addItemToCart(Laptop laptop) {
        int index = -1;
        for (int i = 0; i < this.lineItems.size(); i++) {
            if (Objects.equals(this.lineItems.get(i).getLaptop().getId(), laptop.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            this.lineItems.add(new LineItem(laptop, 1, laptop.getDiscount(), laptop.getPrice()));
        } else {
            LineItem lineItem = lineItems.get(index);
            lineItem.setDiscount(laptop.getDiscount());
            lineItem.setPrice(laptop.getPrice());
            lineItem.setQuantity(lineItem.getQuantity() + 1);
        }
        this.totalAmount = this.lineItems.stream().reduce(0.0, (res, lineItem) -> res + lineItem.getPrice() * ((100 - lineItem.getDiscount()) / 100) * lineItem.getQuantity(), Double::sum);


    }

    public void editItemInCart(Laptop laptop, int quantity) throws DataNotFoundException {
        int index = -1;
        for (int i = 0; i < this.lineItems.size(); i++) {
            if (Objects.equals(this.lineItems.get(i).getLaptop().getId(), laptop.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new DataNotFoundException("Laptop not found in lineItem");
        } else {
            LineItem lineItem = lineItems.get(index);
            lineItem.setDiscount(laptop.getDiscount());
            lineItem.setPrice(laptop.getPrice());
            lineItem.setQuantity(quantity);
        }
        this.totalAmount = this.lineItems.stream().reduce(0.0, (res, lineItem) -> res + lineItem.getPrice() * ((100 - lineItem.getDiscount()) / 100) * lineItem.getQuantity(), Double::sum);
    }

    public void deleteItemInCart(long laptopId) throws DataNotFoundException {
        int index = -1;
        for (int i = 0; i < this.lineItems.size(); i++) {
            if (Objects.equals(this.lineItems.get(i).getLaptop().getId(), laptopId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new DataNotFoundException("Laptop not found in lineItem");
        } else this.lineItems.remove(index);
        this.totalAmount = this.lineItems.stream().reduce(0.0, (res, lineItem) -> res + lineItem.getPrice() * ((100 - lineItem.getDiscount()) / 100) * lineItem.getQuantity(), Double::sum);
        if (this.lineItems.size() == 0) {
            this.isDelete = true;
        }
    }

    public void deleteAll(){
        this.isDelete = true;
    }


}
