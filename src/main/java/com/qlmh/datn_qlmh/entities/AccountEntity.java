package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="account")
@ToString
public class AccountEntity extends BaseEntity implements Serializable{
    @Id
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String fullname;
    private Integer role;
    private String img;
    private String urlImg;
    private Date birthday;
    private Integer status;
    private String token;
    private Date expiredToken;
}
