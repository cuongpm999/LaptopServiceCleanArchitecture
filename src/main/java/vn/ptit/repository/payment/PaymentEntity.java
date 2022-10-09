package vn.ptit.repository.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Payment;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "total_money", nullable = false)
    private Double totalMoney;

    public Payment toDomain(){
        return new Payment(id,totalMoney);
    }
}
