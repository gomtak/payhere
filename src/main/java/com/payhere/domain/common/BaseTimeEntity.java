package com.payhere.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_datetime")
    private Timestamp createdDatetime;
    @LastModifiedDate
    @Column(name = "updated_datetime")
    private Timestamp updatedDatetime;

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Timestamp getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Timestamp updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTimeEntity that = (BaseTimeEntity) o;
        return Objects.equals(createdDatetime, that.createdDatetime) && Objects.equals(updatedDatetime, that.updatedDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDatetime, updatedDatetime);
    }
}

