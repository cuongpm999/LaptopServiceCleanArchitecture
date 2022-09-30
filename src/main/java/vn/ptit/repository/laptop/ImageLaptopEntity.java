package vn.ptit.repository.laptop;

import lombok.Data;
import vn.ptit.repository.manufacturer.ManufacturerEntity;

import javax.persistence.*;

@Entity
@Table(name = "image_laptops")
@Data
public class ImageLaptopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "source", nullable = false, length = 1000)
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laptop_id", nullable = false)
    private LaptopEntity laptop;

}
