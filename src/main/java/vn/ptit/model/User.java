package vn.ptit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String fullName;
    private String address;
    private String email;
    private String mobile;
    private Boolean sex;
    private Date dateOfBirth;
    private String username;
    private String password;
    private String position;
    private String avatar;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDelete;

    public User(Long id, String fullName, String address, String email, String mobile, Boolean sex, Date dateOfBirth, String username, String password, String position, String avatar, Date createdAt, Date updatedAt, Boolean isDelete) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.position = position;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
    }

    public User(String fullName, String address, String email, String mobile, Boolean sex, Date dateOfBirth, String username, String password, String position, String avatar) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.position = position;
        this.avatar = avatar;
    }
    @SneakyThrows
    public static User create(String fullName, String address, String email, String mobile,
                              Boolean sex, String dateOfBirth, String username,
                              String password, String position, String avatar){
        return new User(fullName, address, email, mobile, sex, new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth), username, password, position, avatar);
    }
    @SneakyThrows
    public void update(String fullName, String address, String email, String mobile,
                       Boolean sex, String dateOfBirth, String position, String avatar){
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        this.position = position;
        this.avatar = avatar;
    }

    public void delete(){
        this.isDelete = true;
    }
}
