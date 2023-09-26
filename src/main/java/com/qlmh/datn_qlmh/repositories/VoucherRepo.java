package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.dtos.request.AccountVoucherDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherAccountDto;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepo extends JpaRepository<VoucherEntity,Long>,JpaSpecificationExecutor<VoucherEntity> {
    @Query("SELECT new com.qlmh.datn_qlmh.dtos.request.AccountVoucherDto(vc,vca) FROM VoucherEntity vc INNER JOIN VoucherAccountEntity vca ON vc.id = vca.voucherId WHERE vc.status = 1 AND vca.username =:username")
    List<AccountVoucherDto> findByUsername(@Param("username") String username);
}
