package vn.ptit.repository.payment;

import vn.ptit.model.Cash;
import vn.ptit.model.DigitalWallet;

public interface IPaymentRepository {
    Cash saveCash(Cash cash);
    DigitalWallet saveDigitalWallet(DigitalWallet digitalWallet);
    Cash getCashById(long id);
    DigitalWallet getDigitalWalletById(long id);
}
