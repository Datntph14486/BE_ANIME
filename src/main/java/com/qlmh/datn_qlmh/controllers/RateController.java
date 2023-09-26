package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.RateImgReq;
import com.qlmh.datn_qlmh.dtos.request.RateReq;
import com.qlmh.datn_qlmh.dtos.response.RateRes;
import com.qlmh.datn_qlmh.services.RateImgService;
import com.qlmh.datn_qlmh.services.RateService;
import com.qlmh.datn_qlmh.services.UploadfileService;
import com.qlmh.datn_qlmh.ultil.UploadDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/rate")
public class RateController {

    private final Path root = Paths.get("uploads");
    @Autowired
    RateService rateService;
    @Autowired
    RateImgService rateImgService;

    @Autowired
    UploadDownload uploadDownload;

    @Autowired
    UploadfileService uploadfileService;
    @GetMapping("/get-by-productid")
    public List<RateRes> listProducts(@RequestParam("product-id") Integer projectId) {
        List<RateRes> products = rateService.getByProductID(projectId);
        return products;

    }
    @GetMapping("/get-by-username")
    public ResponseEntity<List<RateRes>> getByUsername(Authentication authentication) {
        return ResponseEntity.ok(rateService.getByUsername(authentication));

    }

    @GetMapping("/getById")
    public RateRes getById(Authentication authentication,@RequestParam("rate-id") Integer rateId) {
        return rateService.getByID(authentication,rateId);

    }

    @GetMapping("/getUsername")
    public String getUsername(Authentication authentication){
        return rateService.getUsername(authentication);
    }

    @GetMapping("/get-all")
    public List<RateRes> getAll() {
        return rateService.getAll();
    }

    @PostMapping("/craete")
    public RateRes create(Authentication authentication,@RequestBody RateReq rateReq) {
        return rateService.create(authentication,rateReq);
    }

    @PutMapping("/update")
    public RateRes update(Authentication authentication,@RequestBody RateReq rateReq) {
        return rateService.update(authentication,rateReq);
    }

    @PostMapping("/create-img")
    public RateImgReq craeteImg(@RequestBody List<RateImgReq> rateImgReq) {
        return rateImgService.create(rateImgReq);
    }

    @PutMapping("/update-img")
    public RateImgReq updateImg(Authentication authentication, @RequestBody List<RateImgReq> rateImgReq) {
        return rateImgService.update(rateImgReq);
    }

    @GetMapping("/get-by-rateid")
    public List<RateImgReq> getImgByRate(@RequestParam("id") Integer id) {
        System.out.println(id);
        return rateImgService.getByRateID(id);
    }

    @GetMapping("/get-by-id")
    public RateImgReq getImgById(@RequestParam("id") Integer id) {
        return rateImgService.getById(id);

    }

    @DeleteMapping("delete-by-id")
    public RateRes deletedByid(@RequestParam("id") Integer id){
        return rateService.delete(id);
    }

    @PostMapping("/upload")
    public  String[] upload(@RequestParam("files") MultipartFile[] files) {
        if(files.length >0){
            String[] a =  uploadfileService.upload(files);
            return a;
        }else{
            return null;
        }

    }

    @GetMapping("/show")
    public ResponseEntity<byte[]> show(@RequestParam("url") String url) throws IOException{

        return ResponseEntity.ok(uploadfileService.show(url));
    }
}
