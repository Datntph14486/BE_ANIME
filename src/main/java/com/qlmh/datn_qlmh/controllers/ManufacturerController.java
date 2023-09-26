package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ManufacturerReq;
import com.qlmh.datn_qlmh.dtos.response.ManufacturerResp;
import com.qlmh.datn_qlmh.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
    @Autowired
    ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<List<ManufacturerResp>> getAll() {
        return ResponseEntity.ok(manufacturerService.getALl());
    }

    @PostMapping()
    public ResponseEntity<ManufacturerReq> create(@RequestBody ManufacturerReq manufacturerReq) {
        return ResponseEntity.ok(manufacturerService.create(manufacturerReq));
    }
    @PutMapping()
    public ResponseEntity<ManufacturerReq> update(@RequestBody ManufacturerReq manufacturerReq){
        return ResponseEntity.ok(manufacturerService.update(manufacturerReq));
    }
}
