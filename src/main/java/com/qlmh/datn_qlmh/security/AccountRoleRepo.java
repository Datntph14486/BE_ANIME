package com.qlmh.datn_qlmh.security;

import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRoleRepo extends JpaRepository<AccountRoleEntity,Long> {
    @Query("SELECT new AccountRoleEntity(are.id,are.userName,are.roleId,re)  FROM AccountRoleEntity are JOIN " +
            "RoleEntity re on are.roleId = re.id" +
            " WHERE are.userName =:us")
    public List<AccountRoleEntity> getAccountRoleEntityByUserName(@Param("us") String username);
    @Query("SELECT are FROM AccountRoleEntity are where  are.userName=:us and are.roleId=:id")
    public Optional<AccountRoleEntity> getByRoleAndUsername(@Param("id") Long id, @Param("us") String us);

    @Query("SELECT P FROM AccountRoleEntity P WHERE P.userName=?1")
    List<AccountRoleEntity>  getByUsername(String username);
}
