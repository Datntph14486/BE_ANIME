
package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "return_request_figure_entity")
public class ReturnRequestFigureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "create_date")
    private Date creatDate = new Date();
    @Column(name = "status")
    private Integer status;
    @Column(name = "user_name")
    private String userName;
}
