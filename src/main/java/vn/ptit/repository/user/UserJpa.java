package vn.ptit.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpa extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByIsDeleteFalse();
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity findByMobile(String mobile);
}
