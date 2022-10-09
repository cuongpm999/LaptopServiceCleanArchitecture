package vn.ptit.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashJpa extends JpaRepository<CashEntity,Long> {
}
