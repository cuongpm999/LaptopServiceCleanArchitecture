package vn.ptit.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpa extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByIsDeleteFalse(Pageable pageable);
    UserEntity findByUsernameAndIsDeleteFalse(String username);
    UserEntity findByEmailAndIsDeleteFalse(String email);
    UserEntity findByMobileAndIsDeleteFalse(String mobile);
}
