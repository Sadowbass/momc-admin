package com.momc.admin.domain.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity<T> {

    @CreatedDate
    protected LocalDateTime createdDate;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    public T setDefaultDateForBulkInsert() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();

        return (T) this;
    }
}
