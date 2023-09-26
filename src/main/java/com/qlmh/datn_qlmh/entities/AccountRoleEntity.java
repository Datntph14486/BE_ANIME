package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_role")
public class AccountRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "username_id")
    private String userName;
    @Transient
    private RoleEntity roleEntity;
    public AccountRoleEntity(Long id,String us,Long rid,RoleEntity roleEntity){
        this.id=id;
        this.userName=us;
        this.roleId=rid;
        this.roleEntity=roleEntity;

    }
    public AccountRoleEntity(String us,Long rid){
        this.userName=us;
        this.roleId=rid;

    }
}
