package com.qlmh.datn_qlmh.controllers;
import com.google.gson.JsonObject;
import com.qlmh.datn_qlmh.configs.Config;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.CartRequest;
import com.qlmh.datn_qlmh.dtos.response.CartResponse;
import com.qlmh.datn_qlmh.dtos.response.DetailCartResponse;
import com.qlmh.datn_qlmh.dtos.response.PaymentResponse;
import com.qlmh.datn_qlmh.services.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private ICartService iCartService;
    @GetMapping(value = "/{user_name}", produces = "application/json")
    public ResponseEntity<?> getListCartDetailByCartId(@PathVariable("user_name") String userName) {
        CartResponse getCartResponse = iCartService.findById(userName);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, getCartResponse));
    }
    @GetMapping(value = "/count", produces = "application/json")
    public Long count( @RequestParam("username") String username) {
      return this.iCartService.count(username);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCart( @RequestBody CartRequest request) {
        DetailCartResponse cartDetailDTO = iCartService.addToCart(request);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, cartDetailDTO));
    }

    @DeleteMapping(value="/{id}",produces = "application/json")
    public ResponseEntity<?> deleteCart(@PathVariable("id") Integer id) {
        DetailCartResponse cartDetailDTO = iCartService.delete(id);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, cartDetailDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCart(@PathVariable("id") Integer id, @RequestParam("amount") int amount) {
        DetailCartResponse cartDetailDTO = iCartService.update(id, amount);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, cartDetailDTO));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteCartDetailByUserId(@Validated @RequestBody List<Integer> id) {
        DetailCartResponse cartDetailDTO = iCartService.deleteListCart(id);
        return ResponseEntity.ok(cartDetailDTO);
    }
    @DeleteMapping("/list/delete/{user_name}")
    public ResponseEntity<?> deleteCartDetailByUserId(@PathVariable("user_name") String user_name) {
        DetailCartResponse cartDetailDTO = iCartService.deleteAllCart(user_name);
        return ResponseEntity.ok(cartDetailDTO);
    }

}
