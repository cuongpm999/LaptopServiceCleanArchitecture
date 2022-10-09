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
public class AddItemToCartService {
    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;
    private final ILaptopRepository laptopRepository;


    public AddItemToCartService(ICartRepository cartRepository,
                                IUserRepository userRepository,
                                ILaptopRepository laptopRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.laptopRepository = laptopRepository;
    }

    @SneakyThrows
    public void addItemToCart(String username, long itemId){
        Cart cartCurrent = cartRepository.findCurrentCart(username);
        if(cartCurrent == null){
            User user = userRepository.getByUsername(username);
            if(user == null){
                throw new DataNotFoundException("User not found");
            }
            cartCurrent = Cart.create(user);
        }
        Laptop laptop = laptopRepository.findById(itemId);
        if(laptop == null){
            throw new DataNotFoundException("Laptop not found");
        }
        cartCurrent.addItemToCart(laptop);

        cartRepository.save(cartCurrent);
    }
}
