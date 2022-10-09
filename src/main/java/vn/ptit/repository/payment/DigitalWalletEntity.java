package vn.ptit.repository.payment;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Cash;
import vn.ptit.model.DigitalWallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "digital_wallets")
@PrimaryKeyJoinColumn(name = "payment_id")
@Data
@NoArgsConstructor
public class DigitalWalletEntity extends PaymentEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public DigitalWalletEntity(Long id, Double totalMoney, String name) {
        super(id, totalMoney);
        this.name = name;
    }

    public static DigitalWalletEntity fromDomain(DigitalWallet digitalWallet) {
        return new DigitalWalletEntity(digitalWallet.getId(), digitalWallet.getTotalMoney(), digitalWallet.getName());
    }

    public DigitalWallet toDomain() {
        return new DigitalWallet(getId(), getTotalMoney(), name);
    }
}
