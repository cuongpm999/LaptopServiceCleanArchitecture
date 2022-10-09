package vn.ptit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ptit.repository.laptop.LaptopEntity;

@Repository
public interface CartJpa extends JpaRepository<CartEntity, Long> {
    CartEntity findByIsDeleteFalseAndUser_Username(String username);
    CartEntity findByIdAndIsDeleteFalse(long id);
}
