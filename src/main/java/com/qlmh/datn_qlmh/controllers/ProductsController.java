package com.qlmh.datn_qlmh.controllers;
import com.qlmh.datn_qlmh.dtos.Items.FilterItems;
import com.qlmh.datn_qlmh.dtos.Items.PageItem;
import com.qlmh.datn_qlmh.dtos.Items.SearchFilterPage;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.ProductItems;
import com.qlmh.datn_qlmh.dtos.request.ProductReq;
import com.qlmh.datn_qlmh.dtos.response.ProductResp;
import com.qlmh.datn_qlmh.services.ProductService;
import com.qlmh.datn_qlmh.services.UploadfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
public class ProductsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UploadfileService uploadfileService;


    @GetMapping()
    public ResponseEntity<List<ProductResp>> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ProductReq productReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,productService.create(productReq) ));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody ProductReq productReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,productService.update(productReq) ));
    }
    @PutMapping("available")
    public ResponseEntity<?> updateAvailable(@RequestParam("id") Integer id, @RequestParam(value = "available", defaultValue = "1") int available){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,productService.updateStatus(id, available) ));
    }

    @GetMapping("title")
    public ResponseEntity<List<ProductResp>> getAllByTitle(@RequestParam("id") Integer id){
        return ResponseEntity.ok(productService.getProductByTitle(id));
    }
    @GetMapping("detail")
    public ResponseEntity<ProductResp> getByID( @RequestParam("id") Integer id ){
        return ResponseEntity.ok(productService.getByID(id));
    }

    @GetMapping ("getAll")
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "sortBy", required = false) String sortBy,
                                           @RequestParam(value = "sortDirection", required = false) String sortDirection,
                                           @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
                                           @RequestParam(value = "manufacturerID", required = false) List<Integer> manufacturerID,
                                           @RequestParam(value = "seriesID", required = false) List<Integer> seriesID,
                                           @RequestParam(value = "ratio", required = false) List<String> ratio,
                                           @RequestParam(value = "minPrice", required = false) List<Double> minPrice,
                                           @RequestParam(value = "maxPrice", required = false) List<Double> maxPrice,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "available", required = false)Integer available,
                                           @RequestParam(value = "title", required = false)Integer title)
    {
        ProductItems request = new ProductItems();
        request.setAvailable(available);
        request.setName(name);
        request.setRatio(ratio);
        request.setMaxPrice(maxPrice);
        request.setMinPrice(minPrice);
        request.setCategoryIds(categoryIds);
        request.setManufacturerID(manufacturerID);
        request.setSeriesID(seriesID);
        request.setTitle(title);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, productService.getAll(request,pageNum, pageSize, sortBy, sortDirection )));
    }
    @GetMapping("detail_product")
    public ResponseEntity<?> detailProduct( @RequestParam("id") Integer id ){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, productService.detailProduct(id)));
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
    @PutMapping("/replace-image/{index}")
    public ResponseEntity<String> replaceImage(@PathVariable("index") int index, @RequestParam("file") MultipartFile file) {
        List<byte[]> images = null;
        if(index < 0 || index >= images.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        try {
            images.set(index, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Image replaced");
    }


    @GetMapping("/show")
    @Cacheable(key = "#url",cacheManager = "imageCache",value = "images")
    public ResponseEntity<byte[]> show(@RequestParam("url") String url) throws IOException {

        return ResponseEntity.ok(uploadfileService.show(url));
    }
}
