package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

@Data
@NoArgsConstructor
public class Cash extends Payment{
    private Double cashTendered;

    public Cash(Double totalMoney, Double cashTendered) {
        super(totalMoney);
        this.cashTendered = cashTendered;
    }

    public Cash(Long id, Double totalMoney, Double cashTendered) {
        super(id, totalMoney);
        this.cashTendered = cashTendered;
    }

    public static Cash create(Double totalMoney, Double cashTendered){
        validateData(totalMoney,cashTendered);
        return new Cash(totalMoney,cashTendered);
    }

    @SneakyThrows
    private static void validateData(Double totalMoney, Double cashTendered){
        if (totalMoney == null) {
            throw new InvalidDataException("Required field [totalMoney]");
        }
        if(cashTendered == null){
            throw  new InvalidDataException("Required field [cashTendered]");
        }
    }
}
