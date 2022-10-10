package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import vn.ptit.exception.InvalidDataException;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Integer star;
    private String content;
    private User user;
    private Laptop laptop;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDelete;

    public Comment(Integer star, String content, User user, Laptop laptop) {
        this.star = star;
        this.content = content;
        this.user = user;
        this.laptop = laptop;
    }

    public static Comment create(Integer star, String content, User user, Laptop laptop){
        validateData(star, content);
        return new Comment(star, content, user, laptop);
    }

    @SneakyThrows
    private static void validateData(Integer star, String content){
        if (star == null) {
            throw new InvalidDataException("Required field [star]");
        }
        if(content == null){
            throw new InvalidDataException("Required field [content]");
        }
    }
}
