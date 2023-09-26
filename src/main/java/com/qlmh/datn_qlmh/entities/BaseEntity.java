package com.qlmh.datn_qlmh.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    protected Date createDate;
    @Column(name = "update_date")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateDate;
    @CreatedBy
    @Column(name = "create_by")
    protected String createBy;
    @Column(name = "update_by")
    protected String updateBy;

}
