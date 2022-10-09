package vn.ptit.repository.cart;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.model.Cart;
import vn.ptit.model.LineItem;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartRepository implements ICartRepository {
    private final CartJpa cartJpa;
    private final LineItemJpa lineItemJpa;

    public CartRepository(CartJpa cartJpa, LineItemJpa lineItemJpa) {
        this.cartJpa = cartJpa;
        this.lineItemJpa = lineItemJpa;
    }

    @Override
    public void save(Cart cart) {
        CartEntity cartEntity = CartEntity.fromDomain(cart);
        cartJpa.save(cartEntity);
    }

    @Override
    public Cart findCurrentCart(String username) {
        CartEntity cartEntity = cartJpa.findByIsDeleteFalseAndUser_Username(username);
        if (cartEntity != null) {
            return cartEntity.toDomain();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteItemInCart(Cart cart) {
        List<Long> lineItemIdCurrents = lineItemJpa.findByCart_Id(cart.getId()).stream().map(LineItemEntity::getId).collect(Collectors.toList());
        lineItemIdCurrents.removeAll(cart.getLineItems().stream().map(LineItem::getId).collect(Collectors.toList()));
        lineItemJpa.deleteLinItemsWithIds(lineItemIdCurrents);

        save(cart);
    }
}
