package vn.ptit.repository.laptop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopJpa extends JpaRepository<LaptopEntity, Long> {
    LaptopEntity findByIsDeleteFalseAndId(long id);
}
