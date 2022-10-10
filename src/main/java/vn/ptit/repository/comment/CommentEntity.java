package vn.ptit.repository.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.model.Comment;
import vn.ptit.repository.BaseEntity;
import vn.ptit.repository.laptop.LaptopEntity;
import vn.ptit.repository.user.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "star", nullable = false)
    private Integer star;
    @Column(name = "content", nullable = false, length = 1000)
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "laptop_id", nullable = false)
    private LaptopEntity laptop;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @PrePersist
    void preInsert() {
        this.isDelete = false;
    }

    public static CommentEntity fromDomain(Comment comment) {
        return new CommentEntity(comment.getId(), comment.getStar(), comment.getContent(),
                UserEntity.fromDomain(comment.getUser()), LaptopEntity.fromDomain(comment.getLaptop()), comment.getIsDelete());
    }

    public Comment toDomain() {
        return new Comment(id, star, content, user.toDomain(), laptop.toDomain(),
                new Date(getCreatedAt().getTime()),
                new Date(getUpdatedAt().getTime()), isDelete);
    }
}
