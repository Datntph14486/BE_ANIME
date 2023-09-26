package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DetailBillRepo extends JpaRepository<DetailBillEntity, Integer>, JpaSpecificationExecutor<DetailBillEntity> {
//@Query("select db.id from DetailBillEntity  db join BillEntity  b on db.billId = b.id")
    List<DetailBillEntity> findByBillId(Integer idBill);
    DetailBillEntity findByBillIdAndProductId(Integer bill, Integer product);
    DetailBillEntity findByIdAndProductId(Integer bill, Integer product);
    Optional<DetailBillEntity> findById(Integer id);


}