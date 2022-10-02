package vn.ptit.repository.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Shipment;
import vn.ptit.repository.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name",nullable = false, length = 255)
    private String name;
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public static ShipmentEntity fromDomain(Shipment shipment) {
        return new ShipmentEntity(shipment.getId(), shipment.getName(), shipment.getAddress(),
                shipment.getPrice(), shipment.getIsDelete());
    }

    public Shipment toDomain() {
        return new Shipment(id, name, address, price,
                new Date(getCreatedAt().getTime()),
                new Date(getUpdatedAt().getTime()), isDelete);
    }
}
