package vn.ptit.repository.shipment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentJpa extends JpaRepository<ShipmentEntity, Long> {
    List<ShipmentEntity> findByIsDeleteFalse(Pageable pageable);
}
