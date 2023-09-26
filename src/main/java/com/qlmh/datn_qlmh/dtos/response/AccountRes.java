package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import com.qlmh.datn_qlmh.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRes {
    private String userName;
    private String email;
    private String phone;
    private String fullname;
    private Long role;
    private String urlImg;
    private Date birthday;
    private Integer status;

    List<AccountRoleEntity> accountRoleEntities;


}
