package vn.ptit.repository.laptop;

import lombok.Data;
import vn.ptit.repository.BaseEntity;
import vn.ptit.repository.manufacturer.ManufacturerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "laptops")
@Data
public class LaptopEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "category", nullable = false)
    private int category;
    @Column(name = "cpu", nullable = false, length = 255)
    private String cpu;
    @Column(name = "hard_drive", nullable = false, length = 255)
    private String hardDrive;
    @Column(name = "ram", nullable = false, length = 255)
    private String ram;
    @Column(name = "vga", nullable = false, length = 255)
    private String vga;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "discount", nullable = false)
    private double discount;
    @Column(name = "screen", nullable = false)
    private double screen;
    @Column(name = "video", nullable = false, length = 255)
    private String video;
    @Column(name = "specifications", nullable = false, columnDefinition = "LONGTEXT")
    private String specifications;
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private ManufacturerEntity manufacturer;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "laptop")
    private List<ImageLaptopEntity> images = new ArrayList<>();

    public void addImage(ImageLaptopEntity image){
        image.setLaptop(this);
        this.images.add(image);
    }
}
