package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.PaymentRequest;
import com.qlmh.datn_qlmh.dtos.response.BillResponse;
import com.qlmh.datn_qlmh.entities.PaymentEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.repositories.PaymentRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class PaymentServiceImp {
    private final PaymentRepo paymentRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImp(PaymentRepo paymentRepo, ModelMapper modelMapper) {
        this.paymentRepo = paymentRepo;
        this.modelMapper = modelMapper;
    }

    public PaymentRequest create(PaymentRequest request) {
        if (request.getCodeBill() == null) {
            throw new InvalidArgumentException("Code bill not null");
        }
        PaymentEntity paymentEntityOption = this.paymentRepo.findByTransactionNo(request.getTransactionNo());

        if(paymentEntityOption == null){
            PaymentEntity paymentEntity  =modelMapper.map(request, PaymentEntity.class);
            this.paymentRepo.save(paymentEntity);
            return modelMapper.map(paymentEntity, PaymentRequest.class);
        }else{
            paymentEntityOption = modelMapper.map(request, PaymentEntity.class);
            return modelMapper.map(paymentEntityOption, PaymentRequest.class);
        }

    }
    public PaymentRequest findPay(String codeBill) {
        if (codeBill == null) {
            throw new InvalidArgumentException("Code bill not null");
        }
        PaymentEntity paymentEntityOption = this.paymentRepo.findByCodeBill(codeBill);

        return modelMapper.map(paymentEntityOption, PaymentRequest.class);

    }
}
