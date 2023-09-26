package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.VoucherAccountDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherAccountReq;
import com.qlmh.datn_qlmh.entities.AccountEntity;
import com.qlmh.datn_qlmh.entities.VoucherAccountEntity;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import com.qlmh.datn_qlmh.exceptions.EntityAlreadyExistsException;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.AccountRepo;
import com.qlmh.datn_qlmh.repositories.VoucherAccountRepo;
import com.qlmh.datn_qlmh.repositories.VoucherRepo;
import com.qlmh.datn_qlmh.services.VoucherAccountService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherAccountServiceImpl implements VoucherAccountService {
    private final VoucherRepo voucherRepo;
    private final VoucherAccountRepo voucherAccountRepo;
    private final AccountRepo accountRepo;

    private final Mapper mapper;

    public VoucherAccountServiceImpl(VoucherRepo voucherRepo,VoucherAccountRepo voucherAccountRepo,AccountRepo accountRepo,Mapper mapper){
        this.voucherRepo = voucherRepo;
        this.accountRepo = accountRepo;
        this.voucherAccountRepo = voucherAccountRepo;
        this.mapper=mapper;

    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public VoucherAccountReq add(VoucherAccountReq voucherAccountReq) {
//        voucherRepo.findById(voucherAccountReq.getVoucherId()).orElseThrow(()->new NotFoundException("Voucher not found : " +voucherAccountReq.getVoucherId()));
//        accountRepo.findById(voucherAccountReq.getUsername()).orElseThrow(()->new NotFoundException("Account not found : " +voucherAccountReq.getUsername()));
//        voucherAccountRepo.findByAccountIdAndVoucherId(voucherAccountReq.getUsername(),voucherAccountReq.getVoucherId()).orElseThrow(()->new EntityAlreadyExistsException("Voucher already exists"));
//        VoucherAccountEntity voucherAccountEntity = mapper.toVoucherAccountEntity(voucherAccountReq);
//        voucherAccountRepo.save(voucherAccountEntity);
        if(voucherAccountReq.getId() ==null){
            Optional<VoucherAccountEntity> optional = voucherAccountRepo.findByAccountIdAndVoucherId(voucherAccountReq.getUsername(),voucherAccountReq.getVoucherId());
            if(optional.isPresent()){
                throw new EntityAlreadyExistsException("Voucher already exists");
            }
            System.out.println("logger");
            VoucherAccountEntity voucherAccountEntity = mapper.toVoucherAccountEntity(voucherAccountReq);
            voucherAccountEntity.setStatus(Constant.VoucherAccountStatus.USEABLE);
            voucherAccountEntity.setCode(""+new Date().getTime());
            voucherAccountEntity = voucherAccountRepo.save(voucherAccountEntity);
            return new VoucherAccountReq(voucherAccountEntity);
        }
        else{
            VoucherAccountEntity voucherAccountEntity = voucherAccountRepo.findById(voucherAccountReq.getId()).orElseThrow(()->new NotFoundException("Voucher not found : " +voucherAccountReq.getVoucherId()));
            if(voucherAccountEntity.getStatus() ==null) throw  new InvalidArgumentException("Voucher already used");
            voucherAccountEntity.setStatus(Constant.VoucherAccountStatus.USEABLE);
            voucherAccountEntity = voucherAccountRepo.save(voucherAccountEntity);
            return new VoucherAccountReq(voucherAccountEntity);
        }
    }

    @Override
    public VoucherAccountReq disable(VoucherAccountReq voucherAccountReq) {
        if(voucherAccountReq.getId() == null) {
            throw new InvalidArgumentException("Id must not be null");
        }
        VoucherAccountEntity voucherAccountEntity = voucherAccountRepo.findById(voucherAccountReq.getId()).orElseThrow(()->new NotFoundException("Voucher not found : " +voucherAccountReq.getId()));
        if(voucherAccountEntity.getStatus()==null) throw new InvalidArgumentException("Can not disable used voucher");
        voucherAccountEntity.setStatus(Constant.VoucherAccountStatus.NON_ACTIVE);
        voucherAccountEntity = voucherAccountRepo.save(voucherAccountEntity);
        VoucherAccountReq v = mapper.toVoucherAccountReq(voucherAccountEntity);
        v.setActive(false);
        return v;
    }

    @Override
    public void useVoucher(VoucherAccountReq voucherAccountReq) {
        // TODO get user from authentication and check
    }

    @Override
    public VoucherAccountDto getAllById(Long id, Pageable pageable,String username,String fullname) {
        VoucherEntity voucherEntity = voucherRepo.findById(id).orElseThrow(()->new NotFoundException("Voucher not found : " +id));
        // get all account
        // TODO : find all user active instead of find all user
        Specification<AccountEntity> accountEntitySpecification = (root, query, criteriaBuilder) -> {
          return criteriaBuilder.and(
                  QueryUtils.buildLikeFilter(root,criteriaBuilder,fullname,"fullname"),
                  QueryUtils.buildLikeFilter(root,criteriaBuilder,username,"userName")
          );
        };
        accountEntitySpecification.and(
                (root, query, criteriaBuilder) -> {
                    return criteriaBuilder.equal(root.get("status"),true);
                }
        );
        Page<AccountEntity> accountEntities = accountRepo.findAll(accountEntitySpecification,pageable);
        // get all account voucher by voucherID and convert to VoucherAccountReq
        List<VoucherAccountReq> voucherAccountEntities = voucherAccountRepo.getAllByVoucherId(id).stream().map((item)->new VoucherAccountReq(item.getId(),item.getCode(),item.getStatus()==null?Constant.VoucherAccountStatus.USED:item.getStatus(),id,item.getUsername())).collect(Collectors.toList());
        // get all account voucher not exists in list account voucher and convert to VoucherAccountReq
        List<VoucherAccountReq> allVoucherAccount = accountEntities.toList().stream().map((item)->new VoucherAccountReq(null,null,Constant.VoucherAccountStatus.NON_ACTIVE,id,item.getUserName())).collect(Collectors.toList());
        for(int i=0;i< allVoucherAccount.size();i++){
            for (VoucherAccountReq var2:voucherAccountEntities){
                if(var2.getUsername().equals(allVoucherAccount.get(i).getUsername())){
                    allVoucherAccount.set(i,var2);
                    break;
                }
            }
        }
        return new VoucherAccountDto(mapper.toVoucherReq(voucherEntity), PageData.of(accountEntities,allVoucherAccount));
    }
    @Override
    public List<AccountEntity> findAll(){
        return accountRepo.findAll();
    }

    public AccountEntity accountEntity(String s){
        return accountRepo.findByUsername(s).orElseThrow(()->new NotFoundException("Account not found : "+s));
    }

    @Override
    public VoucherAccountReq getByToken(String token) {
        return null;
    }
}
