package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "voucher_account")
@Getter
@Setter
@ToString
public class VoucherAccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="code")
    private String code;
    @Column(name="status")
    private Boolean status;
    @Column(name="voucher_id")
    private Long voucherId;
    @Column(name="username")
    private String username;

}
