package vn.ptit.repository.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.User;
import vn.ptit.repository.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name",nullable = false, length = 255)
    private String fullName;
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "mobile", nullable = false, length = 15)
    private String mobile;
    @Column(name = "sex", nullable = false)
    private Boolean sex;
    @Column(name = "date_of_birth", nullable = false)
    private Timestamp dateOfBirth;
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Column(name = "position", nullable = false, length = 30)
    private String position;
    @Column(name = "avatar", nullable = false, length = 1000)
    private String avatar;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(user.getId(), user.getFullName(), user.getAddress(),
                user.getEmail(), user.getMobile(), user.getSex(), new Timestamp(user.getDateOfBirth().getTime()),
                user.getUsername(), user.getPassword(), user.getPosition(), user.getAvatar(), user.getIsDelete());
    }

    public User toDomain() {
        return new User(id, fullName, address, email, mobile,
                sex, dateOfBirth, username, password, position, avatar,
                new Date(getCreatedAt().getTime()),
                new Date(getUpdatedAt().getTime()), isDelete);
    }
}
