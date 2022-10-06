package vn.ptit.repository.manufacturer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerJpa extends JpaRepository<ManufacturerEntity,Long> {
    List<ManufacturerEntity> findByIsDeleteFalse(Pageable pageable);
    ManufacturerEntity findByIdAndIsDeleteFalse(long id);
}
