package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.RateImgReq;
import com.qlmh.datn_qlmh.dtos.request.RateReq;
import com.qlmh.datn_qlmh.dtos.response.RateRes;
import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.entities.RateImgEntity;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.RateImgRepo;
import com.qlmh.datn_qlmh.services.RateImgService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateImgServiceImpl implements RateImgService {
    @Autowired
    RateImgRepo rateImgRepo;

    @Autowired
    Mapper mapper;

    @Override
    public RateImgReq getById(Integer imgID) {
        RateImgEntity rateImgEntity = rateImgRepo.findById(imgID).orElseThrow(() -> new NotFoundException("rate img not found : " + imgID));

        return mapper.toRateImgReq(rateImgEntity);
    }

    @Override
    public RateImgReq create(List<RateImgReq> rateImgReqs) {
        List<RateImgEntity> rateImgEntitys = rateImgReqs.stream().map((req) -> mapper.toRateImgEntity(req)).collect(Collectors.toList());

        rateImgRepo.saveAll(rateImgEntitys);
        return null;
    }

    @Override
    public RateImgReq update(List<RateImgReq> rateImgReq) {
        if (rateImgReq.size() > 0) {
            List<RateImgEntity> rateImgEntitys = rateImgRepo.findByRate(rateImgReq.get(0).getRateID());
            if (rateImgEntitys.size() == 0) {
                throw new NotFoundException("rate img not found : ");
            }
            for (RateImgReq img : rateImgReq) {
                if (img.getUrl() == null) {
                    throw new NotFoundException("no img change : ");
                }

            }
            rateImgRepo.deleteAll(rateImgEntitys);
            List<RateImgEntity> rateImgEntityss = rateImgReq.stream().map((req) -> mapper.toRateImgEntity(req)).collect(Collectors.toList());
            rateImgRepo.saveAll(rateImgEntityss);
            return null;
        }
        return null;
    }

    @Override
    public List<RateImgReq> getByRateID(Integer rateID) {
        System.out.println("12345");
        System.out.println("abcr" + rateImgRepo.findByRate(rateID));
        return rateImgRepo.findByRate(rateID).stream().map((entity) -> mapper.toRateImgReq(entity)).collect(Collectors.toList());

    }
}
