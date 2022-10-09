package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Double totalMoney;

    public Payment(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
