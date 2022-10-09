package vn.ptit.service.cart;

import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Cart;
import vn.ptit.model.Laptop;
import vn.ptit.repository.cart.ICartRepository;
import vn.ptit.repository.user.IUserRepository;

@Service
public class DeleteItemInCartService {
    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;

    public DeleteItemInCartService(ICartRepository cartRepository, IUserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public void deleteItemInCart(String username, long itemId){
        Cart cartCurrent = cartRepository.findCurrentCart(username);
        if (cartCurrent == null) {
            throw new DataNotFoundException("Cart not found");
        }
        cartCurrent.deleteItemInCart(itemId);
        cartRepository.deleteItemInCart(cartCurrent);
    }

    @SneakyThrows
    public void deleteAll(String username){
        Cart cartCurrent = cartRepository.findCurrentCart(username);
        if (cartCurrent == null) {
            throw new DataNotFoundException("Cart not found");
        }
        cartCurrent.deleteAll();
        cartRepository.save(cartCurrent);
    }
}
