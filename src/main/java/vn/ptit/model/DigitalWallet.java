package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

@Data
@NoArgsConstructor
public class DigitalWallet extends Payment {
    private String name;

    public DigitalWallet(Double totalMoney, String name) {
        super(totalMoney);
        this.name = name;
    }

    public DigitalWallet(Long id, Double totalMoney, String name) {
        super(id, totalMoney);
        this.name = name;
    }

    public static DigitalWallet create(Double totalMoney, String name) {
        validateData(totalMoney, name);
        return new DigitalWallet(totalMoney, name);
    }

    @SneakyThrows
    private static void validateData(Double totalMoney, String name) {
        if (totalMoney == null) {
            throw new InvalidDataException("Required field [totalMoney]");
        }
        if (name == null) {
            throw new InvalidDataException("Required field [name]");
        }
    }
}
