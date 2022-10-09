package vn.ptit.repository.payment;

import org.springframework.stereotype.Repository;
import vn.ptit.model.Cash;
import vn.ptit.model.DigitalWallet;

import java.util.Optional;

@Repository
public class PaymentRepository implements IPaymentRepository{
    private final CashJpa cashJpa;
    private final DigitalWalletJpa digitalWalletJpa;

    public PaymentRepository(CashJpa cashJpa, DigitalWalletJpa digitalWalletJpa) {
        this.cashJpa = cashJpa;
        this.digitalWalletJpa = digitalWalletJpa;
    }

    @Override
    public Cash saveCash(Cash cash) {
        CashEntity cashEntity =  CashEntity.fromDomain(cash);
        cashEntity = cashJpa.save(cashEntity);
        return cashEntity.toDomain();
    }

    @Override
    public DigitalWallet saveDigitalWallet(DigitalWallet digitalWallet) {
        DigitalWalletEntity digitalWalletEntity = DigitalWalletEntity.fromDomain(digitalWallet);
        digitalWalletEntity = digitalWalletJpa.save(digitalWalletEntity);
        return digitalWalletEntity.toDomain();
    }

    @Override
    public Cash getCashById(long id) {
        Optional<CashEntity> cashOptional = cashJpa.findById(id);
        return cashOptional.map(CashEntity::toDomain).orElse(null);
    }

    @Override
    public DigitalWallet getDigitalWalletById(long id) {
        Optional<DigitalWalletEntity> digitalWalletOptional = digitalWalletJpa.findById(id);
        return digitalWalletOptional.map(DigitalWalletEntity::toDomain).orElse(null);
    }
}
