package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ProductRateReq;
import com.qlmh.datn_qlmh.dtos.request.RateReq;
import com.qlmh.datn_qlmh.dtos.response.RateRes;
import com.qlmh.datn_qlmh.entities.ProductRateEntity;
import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.entities.RateImgEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ProductRateRepo;
import com.qlmh.datn_qlmh.repositories.RateImgRepo;
import com.qlmh.datn_qlmh.repositories.RateRepo;
import com.qlmh.datn_qlmh.services.ProductRateService;
import com.qlmh.datn_qlmh.services.RateService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    RateRepo rateRepo;

    @Autowired
    RateImgRepo rateImgRepo;

    @Autowired
    Mapper mapper;

    @Autowired
    ProductRateService productRateService;

    @Autowired
    ProductRateRepo productRateRepo;

    @Override
    public RateRes create(Authentication authentication,RateReq rateReq) {
        Integer detailBillID = rateReq.getDetailBillID();
        if (detailBillID == null) {
            throw new NotFoundException("DetailBillID is null");
        }
        Integer ProductId = rateReq.getProductID();
        if (ProductId == null) {
            throw new NotFoundException("ProductID is null");
        }
        Integer ratee = rateReq.getRate();
        if (ratee == null) {
            throw new NotFoundException("rate is null");
        }
        List<RateEntity> entity = rateRepo.findByDetailBillID(rateReq.getDetailBillID());
        if(entity.size() == 0) {
            RateEntity rate = mapper.toRateEntity(rateReq);
            rate.setDeleted(false);
            rate.setRightToEdit(false);
            rate = rateRepo.save(rate);

            ProductRateEntity productRateEntity = productRateService.getByProductId(rate.getProductID());
            if (productRateEntity != null) {
                Integer ratecount = productRateEntity.getRateCount() + 1;
                productRateEntity.setRateCount(ratecount);
                Integer starCount = productRateEntity.getStartCount() + rate.getRate();
                productRateEntity.setStartCount(starCount);
                productRateRepo.save(productRateEntity);
            } else {
                ProductRateReq productRateReq = new ProductRateReq(1, 1, rate.getProductID(), rate.getRate());
                ProductRateReq productRateEntity1 = productRateService.create(productRateReq);
            }
            return mapper.toRateRes(rate);
        }else{
            throw new NotFoundException("san pham nay da duoc danh gia");
        }
    }

    @Override
    public RateRes getByID(Authentication authentication,Integer id) {

        RateEntity rateEntity = rateRepo.findById(id).orElseThrow(() -> new NotFoundException("Rate not found : " + id));
        if(rateEntity.getCreateBy().equals(authentication.getName())) {
            if(rateEntity.getRightToEdit()==true){
                throw new NotFoundException(("this rate is not editable"));
            }
            System.out.println("name: " + rateEntity.getUserName());
            List<RateImgEntity> rateImgEntities = rateImgRepo.findByRate(id);
            RateRes rateRes = mapper.toRateRes(rateEntity);
            rateRes.setImgs(rateImgEntities);
            return rateRes;
        }else{
            throw new InvalidArgumentException("khong co quyen ");
        }

    }

    @Override
    public RateRes update(Authentication authentication,RateReq rateReq) {
        if (rateReq.getId() == null) {
            throw new InvalidArgumentException("id is not null ");
        }

        Integer ratee = rateReq.getRate();
        if (ratee == null) {
            throw new NotFoundException("rate is null");
        }
        RateEntity rateEntity = rateRepo.findById(rateReq.getId()).orElseThrow(() ->  new NotFoundException("rate not found : " + rateReq.getId()));
        if(rateEntity.getCreateBy().equals(authentication.getName()) || rateEntity.getRightToEdit()== true){

            if(rateEntity.getRightToEdit()==true){
                throw new NotFoundException(("this rate is not editable"));
            }

            int oldRate = rateEntity.getRate();
            int currentRate = rateReq.getRate();
            int newRate = 0;
            if(currentRate == oldRate){
                newRate = 0;
            }else{
                newRate = currentRate- oldRate;
            }
            ProductRateEntity productRateEntity = productRateService.getByProductId(rateEntity.getProductID());
            productRateEntity.setStartCount(productRateEntity.getStartCount()+newRate);
            productRateRepo.save(productRateEntity);
            rateEntity.setRate(rateReq.getRate());
            rateEntity.setColor(rateReq.isColor());
            rateEntity.setMaterial(rateReq.isMaterial());
            rateEntity.setLackOfAccessories(rateReq.isLackOfAccessories());
            rateEntity.setWrongProduct(rateReq.isWrongProduct());
            rateEntity.setComment(rateReq.getComment());
            rateEntity = rateRepo.save(rateEntity);
            return mapper.toRateRes(rateEntity);
        }else{
            throw new InvalidArgumentException("khong co quyen ");
        }

    }

    @Override
    public List<RateRes> getAll() {
        return rateRepo.findAll().stream().map((entity) -> mapper.toRateRes(entity)).collect(Collectors.toList());
    }

    @Override
    public List<RateRes> getByProductID(Integer productID) {
        List<RateEntity> list = rateRepo.findByProductID(productID,false);
        if(list.size()==0){
            throw new NotFoundException("khong ton tai san pham nao!");
        }

        List<RateRes> res = new ArrayList<RateRes>();

        for (RateEntity rateEntity : list) {
            List<RateImgEntity> rateImgEntities = rateImgRepo.findByRate(rateEntity.getId());
            RateRes rateRes = mapper.toRateRes(rateEntity);
            rateRes.setImgs(rateImgEntities);
            res.add(rateRes);


        }
//        Collections.sort(res, Comparator.comparing(RateRes::getCreateDate)
//                .thenComparing(Comparator.comparing(RateRes::getCreateDate).reversed()));
        Collections.sort(res, Comparator.comparing(RateRes::getCreateDate, Comparator.nullsLast(Comparator.reverseOrder())));

        System.out.println(res);
        return res;
    }

    @Override
    public List<RateRes> getbyStart(Integer start) {
        return null;
    }

    @Override
    public List<RateRes> getByUsername(Authentication authentication) {
       String username = authentication.getName();
        return rateRepo.getByUsername(username).stream().map(entity -> mapper.toRateRes(entity)).collect(Collectors.toList());
    }

    @Override
    public String getUsername(Authentication authentication) {

            return authentication.getName();

    }

    @Override
    public RateRes delete(Integer id) {
        RateEntity rate = rateRepo.findById(id).orElseThrow(() ->  new NotFoundException("rate not found : " + id));
        rate.setDeleted(true);
        ProductRateEntity productRateEntity = productRateService.getByProductId(rate.getProductID());

        if(productRateEntity.getStartCount() ==0){
            productRateEntity.setStartCount(0);
        }else{
            productRateEntity.setStartCount(productRateEntity.getStartCount() - rate.getRate());
        }

        if(productRateEntity.getRateCount()==0){
            productRateEntity.setRateCount(0);
        }else{
            productRateEntity.setRateCount(productRateEntity.getRateCount()-1);
        }

        productRateRepo.save(productRateEntity);
        rateRepo.save(rate);
        return null;
    }


}
