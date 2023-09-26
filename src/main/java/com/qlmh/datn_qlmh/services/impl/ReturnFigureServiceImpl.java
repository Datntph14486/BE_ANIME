
package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;
import com.qlmh.datn_qlmh.entities.ReturnFigureEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ReturnFigureRepo;
import com.qlmh.datn_qlmh.services.ReturnFigureService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnFigureServiceImpl implements ReturnFigureService {
    @Autowired
    ReturnFigureRepo returnRepository;
    @Autowired
    Mapper returnMapper;
    @Override
    public ReturnFigureReq create(ReturnFigureReq returnReq) {
        ReturnFigureEntity returnFigureEntity = returnMapper.toReturnFigureEntity(returnReq);
        returnFigureEntity = returnRepository.save(returnFigureEntity);
        return returnMapper.toReturnReq(returnFigureEntity);
    }

    @Override
    public ReturnFigureReq update(ReturnFigureReq returnReq) {
        if (returnReq.getId() == null) throw new InvalidArgumentException("id is not null ");
        ReturnFigureEntity returnFigure = returnRepository.findById(returnReq.getId()).orElseThrow(() -> new NotFoundException("Not found : " + returnReq.getId()));
        returnFigure = returnMapper.toReturnFigureEntity(returnReq);
        returnFigure = returnRepository.save(returnFigure);
        return returnMapper.toReturnReq(returnFigure);

    }
//    @Override
//    public ReturnReq updateStatus(ReturnReq returnReq) {
//        if (returnReq.getId() == null) throw new InvalidArgumentException("id is not null ");
//        ReturnFigureEntity returnFigure = returnRepository.findById(returnReq.getId()).orElseThrow(() -> new NotFoundException("Not found : " + returnReq.getId()));
//        switch (returnReq.getStatus()){
//            case 0:
//                returnFigure.setStatus(1);
//                break;
//            case 1:
//                returnFigure.setStatus(2);
//                break;
//            case 2:
//                returnFigure.setStatus(3);
//                break;
//            case 3:
//                returnFigure.setStatus(4);
//                break;
//            default:
//                throw new InvalidArgumentException("Invalid status value: " + returnReq.getStatus());
//
//        }
//        returnFigure = returnMapper.toReturnFigureEntity(returnReq);
//        returnFigure = returnRepository.save(returnFigure);
//        return returnMapper.toReturnReq(returnFigure);
//
//    }
    @Override
    public List<ReturnFigureReq> getAll() {
        return returnRepository.findAll().stream().map((entity) -> returnMapper.toReturnReq(entity)).collect(Collectors.toList());
    }
    @Override
    public ReturnFigureReq getById(Integer id) {
        ReturnFigureEntity FigureEntity = returnRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found: " + id));
        return returnMapper.toReturnReq(FigureEntity);
//        Optional<ReturnFigureEntity> optional = returnRepository.findById(id);
//        if (optional.isPresent()) {
//            ReturnFigureEntity entity = optional.get();
//            List<ReturnFigureEntity> list = new ArrayList<>();
//            list.add(entity);
//            return list;
//        }
//        return new ArrayList<>();
        //Trong JPA, hàm findById trả về một Optional
        // chứa một đối tượng hoặc trả về một Optional trống nếu không tìm thấy bản ghi trong database.
    }
}

