package vn.ptit.repository;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    private Timestamp createdTime;

    @Column(name = "updated_time", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedTime;
}
