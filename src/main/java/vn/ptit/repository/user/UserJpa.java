package vn.ptit.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpa extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByIsDeleteFalse(Pageable pageable);
    UserEntity findByUsernameAndIsDeleteFalse(String username);
    UserEntity findByEmailAndIsDeleteFalse(String email);
    UserEntity findByMobileAndIsDeleteFalse(String mobile);
    @Query("select count(distinct o.user) from OrderEntity o where o.user.isDelete = False")
    Integer countUserPurchased();
    @Query("select u from UserEntity u where u.isDelete = False and (u.fullName like %:searchText% or u.email like %:searchText% or u.username like %:searchText% or u.mobile like %:searchText%)")
    List<UserEntity> search(Pageable pageable,@Param("searchText") String searchText);
}
