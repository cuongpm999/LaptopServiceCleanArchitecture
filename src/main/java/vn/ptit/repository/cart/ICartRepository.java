package vn.ptit.repository.cart;

import vn.ptit.model.Cart;

public interface ICartRepository {
    void save(Cart cart);
    Cart findCurrentCart(String username);
    void deleteItemInCart(Cart cart);
    Cart getById(long id);
}
