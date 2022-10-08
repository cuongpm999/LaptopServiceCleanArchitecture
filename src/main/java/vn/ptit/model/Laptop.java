package vn.ptit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        Category(int id, String content) {
            this.id = id;
            this.content = content;
        }

        public static Category getById(Integer number) {
            for (Category e : values()) {
                if (Objects.equals(e.id, number)) {
                    return e;
                }
            }
            return null;
        }
    }

    public Laptop(String name, Integer category, String hardDrive, String ram, String vga, String cpu, Double screen,
                  Double price, Double discount, String video, String specifications, List<String> images) {
        this.name = name;
        this.category = Category.getById(category);
        this.hardDrive = hardDrive;
        this.ram = ram;
        this.vga = vga;
        this.cpu = cpu;
        this.screen = screen;
        this.price = price;
        this.discount = discount;
        this.video = video;
        this.specifications = specifications;

        List<ImageLaptop> imageLaptops = new ArrayList<>();
        for(String image : images){
            imageLaptops.add(new ImageLaptop(image));
        }
        this.images = imageLaptops;
    }

    public static Laptop create(String name, Integer category, String hardDrive, String ram, String vga, String cpu,
                                Double screen, Double price, Double discount, String video, String specifications, List<String> images) {
        validateData(name, category, hardDrive, ram, vga, cpu,
                screen, price, discount, video, specifications, images);
        return new Laptop(name, category, hardDrive, ram, vga, cpu,
                screen, price, discount, video, specifications, images);
    }

    @SneakyThrows
    private static void validateData(String name, Integer category, String hardDrive, String ram, String vga, String cpu,
                                     Double screen, Double price, Double discount, String video, String specifications, List<String> images){
        if (name == null) {
            throw new InvalidDataException("Required field [name]");
        }
        if(category == null){
            throw  new InvalidDataException("Required field [category]");
        }
        if(hardDrive == null){
            throw  new InvalidDataException("Required field [hardDrive]");
        }
        if(ram == null){
            throw  new InvalidDataException("Required field [ram]");
        }
        if(vga == null){
            throw  new InvalidDataException("Required field [vga]");
        }
        if(cpu == null){
            throw  new InvalidDataException("Required field [cpu]");
        }
        if(screen == null){
            throw  new InvalidDataException("Required field [screen]");
        }
        if(price == null){
            throw  new InvalidDataException("Required field [price]");
        }
        if(discount == null){
            throw  new InvalidDataException("Required field [discount]");
        }
        if(video == null){
            throw  new InvalidDataException("Required field [video]");
        }
        if(specifications == null){
            throw  new InvalidDataException("Required field [specifications]");
        }
        if(images == null || images.isEmpty()){
            throw  new InvalidDataException("Required field [images]");
        }
    }

    public void update(String name, Integer category, String hardDrive, String ram, String vga, String cpu,
                       Double screen, Double price, Double discount, String video, String specifications, List<String> images){

        validateData(name, category, hardDrive, ram, vga, cpu,
                screen, price, discount, video, specifications, images);

        this.name = name;
        this.category = Category.getById(category);
        this.hardDrive = hardDrive;
        this.ram = ram;
        this.vga = vga;
        this.cpu = cpu;
        this.screen = screen;
        this.price = price;
        this.discount = discount;
        this.video = video;
        this.specifications = specifications;

        List<ImageLaptop> imageLaptops = new ArrayList<>();
        for(String image : images){
            imageLaptops.add(new ImageLaptop(image));
        }
        this.images = imageLaptops;
    }

    public void delete(){
        this.isDelete = true;
    }
}
