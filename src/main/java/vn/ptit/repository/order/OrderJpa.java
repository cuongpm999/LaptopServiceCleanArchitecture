package vn.ptit.repository.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpa extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser_Username(String username, Pageable pageable);
}
