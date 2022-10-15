package vn.ptit.repository.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpa extends JpaRepository<CommentEntity, Long> {
    @Query("select c from CommentEntity c where c.laptop.id = ?1 and c.isDelete = False")
    List<CommentEntity> findByLaptopIdAndIsDeleteFalse(Pageable pageable, long laptopId);
}
