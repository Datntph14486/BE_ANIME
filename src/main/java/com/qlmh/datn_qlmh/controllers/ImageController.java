package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ImageReq;
import com.qlmh.datn_qlmh.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/images")
public class ImageController {
    @Autowired
    ImageService imageService;

//    @GetMapping()
//    public ResponseEntity<List<ImageReq>> getAll(){
//        return ResponseEntity.ok(imageService.getAll());
//    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody List<ImageReq> imageReq) {
        return  ResponseEntity.ok(imageService.create(imageReq));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody List<ImageReq> imageReq) {
        return ResponseEntity.ok(imageService.update(imageReq));
    }
    @DeleteMapping()
    public void Delete(@RequestBody List<ImageReq> imageReq) {
        this.imageService.delete(imageReq);
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ImageReq imageReq) {
        return ResponseEntity.ok(imageService.update(imageReq));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ImageReq>> getImageByProductID(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(imageService.getImageByProductID(id));
    }
}
