package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.SeriesReq;
import com.qlmh.datn_qlmh.dtos.response.SeriesResp;
import com.qlmh.datn_qlmh.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/series")
public class SeriesController {
    @Autowired
    SeriesService seriesService;

    @GetMapping()
    public ResponseEntity<List<SeriesResp>> getAll() {
        return ResponseEntity.ok(seriesService.getALl());
    }

    @PostMapping()
    public ResponseEntity<SeriesReq> create(@RequestBody SeriesReq seriesReq) {

        return ResponseEntity.ok(seriesService.create(seriesReq));
    }

    @PutMapping()
    public ResponseEntity<SeriesReq> update(@RequestBody SeriesReq seriesReq) {
        return ResponseEntity.ok(seriesService.update(seriesReq));
    }

}
