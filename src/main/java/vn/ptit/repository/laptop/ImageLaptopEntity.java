package vn.ptit.repository.laptop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.ImageLaptop;
import vn.ptit.repository.manufacturer.ManufacturerEntity;

import javax.persistence.*;

@Entity
@Table(name = "image_laptops")
@Data
@NoArgsConstructor
public class ImageLaptopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "source", nullable = false, length = 1000)
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laptop_id", nullable = false)
    private LaptopEntity laptop;

    public ImageLaptopEntity(String source) {
        this.source = source;
    }

    public static ImageLaptopEntity fromDomain(ImageLaptop imageLaptop){
        return new ImageLaptopEntity(imageLaptop.getSource());
    }

    public ImageLaptop toDomain(){
        return new ImageLaptop(id,source);
    }

}
