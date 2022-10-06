package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageLaptop {
    private Long id;
    private String source;

    public ImageLaptop(String source) {
        this.source = source;
    }
}
