package vn.ptit.repository.laptop;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopJpa extends JpaRepository<LaptopEntity, Long> {
    LaptopEntity findByIsDeleteFalseAndId(long id);
    List<LaptopEntity> findByIsDeleteFalse(Pageable pageable);
    @Query(nativeQuery = true, value="select * from laptops where is_delete = false and manufacturer_id = ?1 and id <> ?2 order by RAND() limit ?3")
    List<LaptopEntity> findSameManufacturer(long manufacturerId, long id, int limit);
}
