package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;
import com.qlmh.datn_qlmh.dtos.request.ReturnImageReq;
import com.qlmh.datn_qlmh.dtos.request.ReturnRequestFigureReq;
import com.qlmh.datn_qlmh.services.ReturnImageService;
import com.qlmh.datn_qlmh.services.ReturnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/request")
public class ReturnFigureController {
    @Autowired
    private ReturnRequestService returnRequestService;
    @Autowired
    private ReturnImageService returnImageService;

    @GetMapping("/getAll")
    public List<ReturnRequestFigureReq> getAll(){
        return returnRequestService.getAll();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ReturnRequestFigureReq> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(returnRequestService.getById(id));
    }
    @PostMapping("/insert")
    public ResponseEntity<ReturnRequestFigureReq> insert(@RequestBody ReturnRequestFigureReq returnReq) {
        return ResponseEntity.ok(returnRequestService.create(returnReq)) ;
    }

    @PutMapping("/update")
    public ResponseEntity<ReturnRequestFigureReq> update(@RequestBody ReturnRequestFigureReq returnReq) {
        return ResponseEntity.ok(returnRequestService.update(returnReq)) ;
    }
    @PutMapping("/updateStatus")
    public ResponseEntity<ReturnRequestFigureReq> updateStatus(@RequestBody ReturnRequestFigureReq returnReq) {
        return ResponseEntity.ok(returnRequestService.update(returnReq)) ;
    }

    @PostMapping("/create-img")
    public ReturnImageReq addImg(@RequestBody ReturnImageReq returnImageReq) {
        return returnImageService.create(returnImageReq);
    }

    @PutMapping("/update-img")
    public ReturnImageReq updateImg(@RequestBody ReturnImageReq returnImageReq) {
        return returnImageService.update(returnImageReq);
    }
}