package vn.ptit.repository.laptop;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopJpa extends JpaRepository<LaptopEntity, Long> {
    LaptopEntity findByIsDeleteFalseAndId(long id);
    List<LaptopEntity> findByIsDeleteFalse(Pageable pageable);
}
