package vn.ptit.repository.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderJpa extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser_Username(String username, Pageable pageable);
    @Modifying
        @Query("update OrderEntity o set o.status = ?1, o.updatedAt = ?2 where o.id = ?3")
    void updateOrderStatus(int status, Timestamp updatedAt, long id);
}
