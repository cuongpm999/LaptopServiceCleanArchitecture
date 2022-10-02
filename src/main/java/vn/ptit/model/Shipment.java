package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    private Long id;
    private String name;
    private String address;
    private Double price;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDelete;

    public Shipment(String name, String address, Double price) {
        this.name = name;
        this.address = address;
        this.price = price;
    }

    public static Shipment create(String name, String address, Double price) {
        validateData(name, address, price);
        return new Shipment(name, address, price);
    }

    public void update(String name, String address, Double price){
        validateData(name, address, price);
        this.name = name;
        this.address = address;
        this.price = price;
    }

    public void delete(){
        this.isDelete = true;
    }
    @SneakyThrows
    private static void validateData(String name, String address, Double price){
        if (name == null) {
            throw new InvalidDataException("Required field [name]");
        }
        if(address == null){
            throw  new InvalidDataException("Required field [address]");
        }
        if(price == null){
            throw  new InvalidDataException("Required field [price]");
        }
    }
}
