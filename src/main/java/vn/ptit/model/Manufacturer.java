package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Manufacturer {
    private Long id;
    private String name;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDelete;

    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static Manufacturer create(String name, String address) {
        return new Manufacturer(name, address);
    }
}
