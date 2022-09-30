package vn.ptit.repository.manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerJpa extends JpaRepository<ManufacturerEntity,Long> {
}
