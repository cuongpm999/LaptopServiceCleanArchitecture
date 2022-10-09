package vn.ptit.service.cart;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Cart;
import vn.ptit.model.Laptop;
import vn.ptit.model.User;
import vn.ptit.repository.cart.ICartRepository;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.repository.user.IUserRepository;

@Service
public class EditItemInCartService {
    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;
    private final ILaptopRepository laptopRepository;

    public EditItemInCartService(ICartRepository cartRepository, IUserRepository userRepository, ILaptopRepository laptopRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.laptopRepository = laptopRepository;
    }

    @SneakyThrows
    public void editItemInCart(String username, long itemId, int quantity) {
        Cart cartCurrent = cartRepository.findCurrentCart(username);
        if (cartCurrent == null) {
            throw new DataNotFoundException("Cart not found");
        }
        Laptop laptop = laptopRepository.findById(itemId);
        if (laptop == null) {
            throw new DataNotFoundException("Laptop not found");
        }
        cartCurrent.editItemInCart(laptop, quantity);
        cartRepository.save(cartCurrent);
    }
}
