package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.VoucherAccountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoucherAccountRepo extends JpaRepository<VoucherAccountEntity,Long> {
    @Query("SELECT vc FROM VoucherAccountEntity vc WHERE vc.username =:accountId AND vc.voucherId =:voucherId   ")
    public Optional<VoucherAccountEntity> findByAccountIdAndVoucherId(@Param("accountId") String us, @Param("voucherId") Long vId);
    @Query("SELECT vc FROM VoucherAccountEntity vc WHERE vc.voucherId =:voucherId ")
    public List<VoucherAccountEntity> getAllByVoucherId(@Param("voucherId") Long voucherId);
    VoucherAccountEntity findByCodeAndUsername(String code, String username);



}
