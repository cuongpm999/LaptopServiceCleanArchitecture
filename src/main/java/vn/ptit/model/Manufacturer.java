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

    public Manufacturer(Long id, String name, String address, Date createdAt, Date updatedAt, Boolean isDelete) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
    }

    public static Manufacturer create(String name, String address) {
        return new Manufacturer(name, address);
    }

    public void update(String name, String address){
        this.name = name;
        this.address = address;
    }

    public void delete(){
        this.isDelete = true;
    }
}
