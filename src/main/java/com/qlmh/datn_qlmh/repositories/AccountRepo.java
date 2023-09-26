package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<AccountEntity,String>, JpaSpecificationExecutor<AccountEntity> {
    AccountEntity findByUserNameAndStatus(String userName, int status);
    @Query("SELECT ae FROM AccountEntity ae WHERE ae.userName IN (SELECT va.username FROM VoucherAccountEntity va WHERE va.voucherId =:vid)")
    @Deprecated
    Page<AccountEntity> getAllAccountUsedByVoucherId(@Param("vid") Long id, Pageable pageable);
    //    @Query("SELECT new com.qlmh.datn_qlmh.dtos.request.VoucherAccountDto(new com.qlmh.datn_qlmh.dtos.request.AccountDto(ae.userName)," +
//            "(ae.userName IN (SELECT va.username FROM VoucherAccountEntity va WHERE va.voucherId =:vid))) FROM AccountEntity ae GROUP BY ae.userName")
//    List<VoucherAccountDto> getAllAccountUsedByVoucherIdV2(@Param("vid") Long id);
    @Query("SELECT ae FROM AccountEntity ae WHERE ae.userName =:username")
    public Optional<AccountEntity> findByUsername(@Param("username") String username);
    @Query("SELECT ae FROM AccountEntity ae WHERE ae.userName =:username OR ae.email =:email")
    public Optional<AccountEntity> findByUsernameOrEmail(@Param("username") String username,@Param("email") String email);
    @Query("SELECT ae FROM AccountEntity ae WHERE ae.token =:token")
    public Optional<AccountEntity> findByToken(@Param("token") String token);
    Optional<AccountEntity> findByEmail(String email);
}
