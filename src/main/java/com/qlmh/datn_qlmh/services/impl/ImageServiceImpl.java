package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ImageReq;
import com.qlmh.datn_qlmh.dtos.response.ImageResp;
import com.qlmh.datn_qlmh.entities.ImageEntity;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ImageRepo;
import com.qlmh.datn_qlmh.services.ImageService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final Mapper mapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageServiceImpl(ImageRepo imageRepo, Mapper mapper, ModelMapper modelMapper) {
        this.imageRepo = imageRepo;
        this.mapper = mapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageResp> getAll() {

        return imageRepo.findAll().stream().map((entity) -> mapper.toImageResp(entity)).collect(Collectors.toList());
    }

    @Override
    public List<ImageReq> create(List<ImageReq> imageReqList) {
        List<ImageEntity> imageEntities = imageReqList
                .stream()
                .map(imgResponse -> modelMapper.map(imgResponse, ImageEntity.class))
                .collect(Collectors.toList());
        imageEntities = imageRepo.saveAll(imageEntities);
        List<ImageReq> response = imageEntities
                .stream()
                .map(imgResponse -> modelMapper.map(imgResponse, ImageReq.class))
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public List<ImageReq>  update(List<ImageReq> imageReqList) {
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (ImageReq lst : imageReqList){
            ImageEntity image = this.imageRepo.findById(lst.getId()).orElseThrow(()-> new NotFoundException("Image not found"));
            image.setId(lst.getId());
            image.setUrl(lst.getUrl());
            image.setProductID(lst.getProductID());
            imageEntities.add(image);
        }
        imageEntities = imageRepo.saveAll(imageEntities);
        List<ImageReq> response = imageEntities
                .stream()
                .map(imgResponse -> modelMapper.map(imgResponse, ImageReq.class))
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public ImageReq getById(Integer id) {
        Optional<ImageEntity> imageEntity = imageRepo.findById(id);
        if (imageEntity.isPresent()) {
            return mapper.toImageReq(imageEntity.get());
        }
        return null;
    }

    @Override
    public ImageReq update(ImageReq imageReqList) {
        ImageEntity image = this.imageRepo.findById(imageReqList.getId()).orElseThrow(()-> new NotFoundException("Image not found"));
        BeanUtils.copyProperties(imageReqList, image);
        image = this.imageRepo.save(image);
        return mapper.toImageReq(image);
    }

    @Override
    public void delete(List<ImageReq> imageReqList) {
        List<ImageEntity> imageEntities = imageReqList
                .stream()
                .map(imgResponse -> modelMapper.map(imgResponse, ImageEntity.class))
                .collect(Collectors.toList());
        this.imageRepo.deleteAll(imageEntities);
    }

    @Override
    public List<ImageReq> getImageByProductID(Integer productID) {

        return imageRepo.getImageByProductID(productID).stream().map((entity) -> mapper.toImageReq(entity)).collect(Collectors.toList());
    }
}
