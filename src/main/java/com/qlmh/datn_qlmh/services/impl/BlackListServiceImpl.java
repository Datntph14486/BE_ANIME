
package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.BlackListReq;
import com.qlmh.datn_qlmh.entities.BlackListEntity;
import com.qlmh.datn_qlmh.repositories.BlackListRepo;
import com.qlmh.datn_qlmh.services.BlackListService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepo blackListRepo;
    private final Mapper mapper;
    @Autowired
    public BlackListServiceImpl(BlackListRepo blackListRepo, Mapper mapper){
        this.blackListRepo = blackListRepo;
        this.mapper = mapper;
    }
    @Override
    public BlackListReq save(BlackListReq blackListReq){
        BlackListEntity blackListEntity = mapper.toBlackListEntity(blackListReq);
        blackListEntity=blackListRepo.save(blackListEntity);
        return mapper.toBlackListReq(blackListEntity);
    };
//    BlackListReq update(BlackListReq blackListReq);
    @Override
    public void delete(Long id){
        blackListRepo.deleteById(id);
    };
//    @Override
//    public List<BlackListReq> getBlackListByDiscountId(Long id){
//        return blackListRepo.getByProductId(id).stream().map((entity)->mapper.toBlackListReq(entity)).collect(Collectors.toList());
//    };

}
