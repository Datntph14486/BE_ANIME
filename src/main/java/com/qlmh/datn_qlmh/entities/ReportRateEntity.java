package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="report_rate")
public class ReportRateEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "total_report")
    private Integer totalReport;
    @Column(name = "rate_id")
    private Integer rateID;
}
