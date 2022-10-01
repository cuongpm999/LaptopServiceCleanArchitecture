package vn.ptit.repository.manufacturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Manufacturer;
import vn.ptit.repository.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "manufacturers")
@Data
@NoArgsConstructor
public class ManufacturerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    public ManufacturerEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public static ManufacturerEntity fromDomain(Manufacturer manufacturer) {
        return new ManufacturerEntity(manufacturer.getName(), manufacturer.getAddress());
    }

    public Manufacturer toDomain() {
        return new Manufacturer(id, name,
                address, new Date(getCreatedAt().getTime()),
                new Date(getUpdatedAt().getTime()), isDelete);
    }
}
