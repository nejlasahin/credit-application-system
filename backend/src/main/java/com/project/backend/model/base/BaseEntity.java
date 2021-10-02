package com.project.backend.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @Column(name = "updated_date", nullable = false)
    @LastModifiedDate
    @JsonIgnore
    private Instant updatedDate =Instant.now();

}
