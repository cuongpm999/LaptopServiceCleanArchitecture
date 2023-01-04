package vn.ptit.repository.laptop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpa extends JpaRepository<ImageLaptopEntity,Long> {
    @Modifying
    @Query("delete from ImageLaptopEntity i where i.laptop.id=:latopId")
    void deleteImageByLaptopId(@Param("latopId") Long latopId);

    List<ImageLaptopEntity> findByLaptop_Id(long id);
}
