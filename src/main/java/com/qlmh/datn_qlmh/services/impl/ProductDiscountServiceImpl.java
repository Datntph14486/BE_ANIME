//
//package com.qlmh.datn_qlmh.services.impl;
//
//import com.qlmh.datn_qlmh.dtos.request.BlackListReq;
//import com.qlmh.datn_qlmh.dtos.request.ProductDiscountReq;
//import com.qlmh.datn_qlmh.entities.BlackListEntity;
//import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
//import com.qlmh.datn_qlmh.exceptions.NotFoundException;
//import com.qlmh.datn_qlmh.repositories.BlackListRepo;
//import com.qlmh.datn_qlmh.repositories.ProductDiscountRepo;
//import com.qlmh.datn_qlmh.services.BlackListService;
//import com.qlmh.datn_qlmh.services.ProductDiscountService;
//import com.qlmh.datn_qlmh.services.mapper.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductDiscountServiceImpl implements ProductDiscountService {
//    private final ProductDiscountRepo productDiscountRepo;
//    private final Mapper mapper;
//    @Autowired
//    public ProductDiscountServiceImpl(ProductDiscountRepo productDiscountRepo, Mapper mapper){
//        this.productDiscountRepo = productDiscountRepo;
//        this.mapper = mapper;
//    }
//    @Override
//    public ProductDiscountReq save(ProductDiscountReq productDiscountReq){
//        ProductDiscountEntity productDiscountEntity = mapper.toProductDiscountEntity(productDiscountReq);
//        productDiscountEntity=productDiscountRepo.save(productDiscountEntity);
//        return mapper.toProductDiscountReq(productDiscountEntity);
//    };
//    //    BlackListReq update(BlackListReq blackListReq);
//    @Override
//    public void delete(Long id){
//        ProductDiscountEntity productDiscountEntity = productDiscountRepo.findById(id).orElseThrow(()->new NotFoundException("ProductDiscount not found id :" +id));
//        productDiscountEntity.setStatus(false);
//        productDiscountRepo.save(productDiscountEntity);
//    };
//    @Override
//    public List<ProductDiscountReq> getBlackListByDiscountId(Long id){
//        return productDiscountRepo.getByDiscountId(id).stream().map((entity)->mapper.toProductDiscountReq(entity)).collect(Collectors.toList());
//    };
//
//}
