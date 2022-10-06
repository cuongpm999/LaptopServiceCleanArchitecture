package vn.ptit.repository.laptop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.ImageLaptop;
import vn.ptit.model.Laptop;
import vn.ptit.repository.BaseEntity;
import vn.ptit.repository.manufacturer.ManufacturerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "laptops")
@Data
@NoArgsConstructor
public class LaptopEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "category", nullable = false)
    private Integer category;
    @Column(name = "cpu", nullable = false, length = 255)
    private String cpu;
    @Column(name = "hard_drive", nullable = false, length = 255)
    private String hardDrive;
    @Column(name = "ram", nullable = false, length = 255)
    private String ram;
    @Column(name = "vga", nullable = false, length = 255)
    private String vga;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "discount", nullable = false)
    private Double discount;
    @Column(name = "screen", nullable = false)
    private Double screen;
    @Column(name = "video", nullable = false, length = 255)
    private String video;
    @Column(name = "specifications", nullable = false, columnDefinition = "LONGTEXT")
    private String specifications;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private ManufacturerEntity manufacturer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "laptop")
    private List<ImageLaptopEntity> images = new ArrayList<>();

    public void addImage(ImageLaptopEntity image) {
        image.setLaptop(this);
        this.images.add(image);
    }

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public LaptopEntity(Long id, String name, Integer category, String cpu, String hardDrive, String ram, String vga,
                        Double price, Double discount, Double screen, String video, String specifications, Boolean isDelete,
                        ManufacturerEntity manufacturer) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.cpu = cpu;
        this.hardDrive = hardDrive;
        this.ram = ram;
        this.vga = vga;
        this.price = price;
        this.discount = discount;
        this.screen = screen;
        this.video = video;
        this.specifications = specifications;
        this.isDelete = isDelete;
        this.manufacturer = manufacturer;
    }

    public static LaptopEntity fromDomain(Laptop laptop) {
        LaptopEntity laptopEntity = new LaptopEntity(laptop.getId(), laptop.getName(), laptop.getCategory().getId(),
                laptop.getCpu(), laptop.getHardDrive(), laptop.getRam(), laptop.getVga(), laptop.getPrice(),
                laptop.getDiscount(), laptop.getScreen(), laptop.getVideo(), laptop.getSpecifications(), laptop.getIsDelete(),
                ManufacturerEntity.fromDomain(laptop.getManufacturer()));
        laptop.getImages().forEach(imageLaptop -> {
            laptopEntity.addImage(ImageLaptopEntity.fromDomain(imageLaptop));
        });
        return laptopEntity;
    }

    public Laptop toDomain() {
        List<ImageLaptop> imageLaptops = images.stream().map(ImageLaptopEntity::toDomain).collect(Collectors.toList());
        return new Laptop(id, name, Laptop.Category.getById(category), hardDrive, ram, vga, cpu, screen, price, discount, video,
                new Date(getCreatedAt().getTime()), new Date(getUpdatedAt().getTime()), specifications, manufacturer.toDomain(), imageLaptops, isDelete);
    }
}
