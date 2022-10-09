package vn.ptit.repository.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Cash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="cashs")
@PrimaryKeyJoinColumn(name="payment_id")
@Data
@NoArgsConstructor
public class CashEntity extends PaymentEntity{
    @Column(name = "cash_tendered", nullable = false)
    private Double cashTendered;

    public CashEntity(Long id, Double totalMoney, Double cashTendered) {
        super(id, totalMoney);
        this.cashTendered = cashTendered;
    }

    public static CashEntity fromDomain(Cash cash){
        return new CashEntity(cash.getId(), cash.getTotalMoney(), cash.getCashTendered());
    }

    public Cash toDomain(){
        return new Cash(getId(),getTotalMoney(),cashTendered);
    }
}
