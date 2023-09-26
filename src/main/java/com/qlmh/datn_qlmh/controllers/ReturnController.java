package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;
import com.qlmh.datn_qlmh.services.ReturnFigureService;
import com.qlmh.datn_qlmh.services.ReturnImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/return")
public class ReturnController {
    @Autowired
    private ReturnFigureService returnFigureService;
    @Autowired
    private ReturnImageService returnImageService;

    @GetMapping("/getAll")
    public List<ReturnFigureReq> getAll(){
        return returnFigureService.getAll();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ReturnFigureReq> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(returnFigureService.getById(id));
    }
    @PostMapping("/insert")
    public ResponseEntity<ReturnFigureReq> insert(@RequestBody ReturnFigureReq returnReq) {
        return ResponseEntity.ok(returnFigureService.create(returnReq)) ;
    }

    @PutMapping("/update")
    public ResponseEntity<ReturnFigureReq> update(@RequestBody ReturnFigureReq returnReq) {
        return ResponseEntity.ok(returnFigureService.update(returnReq)) ;
    }
//    @PutMapping("/updateStatus")
//    public ResponseEntity<ReturnReq> updateStatus(@RequestBody ReturnReq returnReq) {
//        return ResponseEntity.ok(returnService.update(returnReq)) ;
//    }
//
//    @PostMapping("/create-img")
//    public ImgReturnReq addImg(@RequestBody ImgReturnReq imgReturnReq) {
//        return imgReturnService.create(imgReturnReq);
//    }
//
//    @PutMapping("/update-img")
//    public ImgReturnReq updateImg(@RequestBody ImgReturnReq imgReturnReq) {
//        return imgReturnService.update(imgReturnReq);
//    }
}
