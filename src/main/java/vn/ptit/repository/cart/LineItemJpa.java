package vn.ptit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemJpa extends JpaRepository<LineItemEntity, Long> {
    List<LineItemEntity> findByCart_Id(long cartId);
    @Modifying
    @Query("delete from LineItemEntity l where l.id in ?1")
    void deleteLinItemsWithIds(List<Long> ids);
}
