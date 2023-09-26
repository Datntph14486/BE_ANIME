package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "timeline")
public class TimeLineEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bill_id")
    private Integer billId;
    @Column(name = "status")
    private int status;
    @Column(name = "DESCRIPTION")
    private String description;
}
