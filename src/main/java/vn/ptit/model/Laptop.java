package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {
    private Long id;
    private String name;
    private Category category;
    private String hardDrive;
    private String ram;
    private String vga;
    private String cpu;
    private Double screen;
    private Double price;
    private Double discount;
    private String video;
    private Date createdAt;
    private Date updatedAt;
    private String specifications;
    private Manufacturer manufacturer;
    private List<ImageLaptop> images;
    private Boolean isDelete;
    public enum Category{
        OFFICE(1, "Văn phòng"),
        GAMING(2,"Gaming"),
        THIN(3,"Mỏng nhẹ, thời trang"),
        GRAPHICS(4,"Đồ họa, kiến trúc");
        private int id;
        private String content;

        Category(int id, String content) {
            this.id = id;
            this.content = content;
        }
    }
}
