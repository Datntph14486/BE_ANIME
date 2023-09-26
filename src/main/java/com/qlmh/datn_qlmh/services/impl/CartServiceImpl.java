package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.Items.DetailCartItems;
import com.qlmh.datn_qlmh.dtos.Items.ImgProductItems;
import com.qlmh.datn_qlmh.dtos.Items.PageItem;
import com.qlmh.datn_qlmh.dtos.request.CartRequest;
import com.qlmh.datn_qlmh.dtos.response.CartResponse;
import com.qlmh.datn_qlmh.dtos.response.DetailCartResponse;
import com.qlmh.datn_qlmh.entities.*;
import com.qlmh.datn_qlmh.exceptions.BadRequestException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.ICartService;
import com.qlmh.datn_qlmh.utilities.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Log4j2
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepo cartRepo;
    private final DetailCartRepo detailCartRepo;
    private final ProductRepo productRepo;
    private final AccountRepo accountRepo;
    private final ModelMapper modelMapper;
    private final ImageRepo imageRepo;
    private static int status =1;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, DetailCartRepo detailCartRepo, ProductRepo productRepo, AccountRepo accountRepo, ModelMapper modelMapper, ImageRepo imageRepo) {
        this.cartRepo = cartRepo;
        this.detailCartRepo = detailCartRepo;
        this.productRepo = productRepo;
        this.accountRepo = accountRepo;
        this.modelMapper = modelMapper;
        this.imageRepo = imageRepo;
    }

    @Override
    public DetailCartResponse addToCart(CartRequest request) {
        log.info("Add to cart service : Start");

        CartEntity cartEntity = new CartEntity();

        DetailCartResponse response = new DetailCartResponse();

        Integer productId = request.getProductId();
        int amount = request.getAmount();
        String userId = request.getUserName();
        String language = "";
        Integer cartId = null;

        Optional<CartEntity> cartEntityOptional = this.cartRepo.findByUserName(userId);
        if (cartEntityOptional.isEmpty()) {
            cartEntity.setUserName(userId);
            cartEntity.setCreateDate(new Date());
            cartEntity = this.cartRepo.save(cartEntity);
            cartId = cartEntity.getId();

        } else {
            cartId = cartEntityOptional.get().getId();
        }
        DetailCartEntity detailCartEntity = new DetailCartEntity();
        ProductEntity productEntity = this.productRepo.findByIdAndStatus(productId, status);
        if (ObjectUtils.isEmpty(productEntity)) {
            throw new NotFoundException(MessageUtils.get(language, "msg.http-status.product.notfound"));

        } else {
            if (productEntity.getQuantity() <= 0) {
                throw new NotFoundException(MessageUtils.get(language, "msg.http-status.product.amount"));
            }
            detailCartEntity.setProductId(productEntity.getId());

            DetailCartEntity detailCart = this.detailCartRepo.findByCartIdAndProductId(cartId, productEntity.getId());
            if (ObjectUtils.isEmpty(detailCart)) {
                if (detailCart != null) {
                    if (Objects.equals(detailCart.getAmount(), productEntity.getQuantity())) {
                        throw new BadRequestException(MessageUtils.get(language, "msg.http-status.product-cart.amount"));
                    }
                }
                detailCartEntity.setCartId(cartId);
                if (amount > productEntity.getQuantity()) {
                    throw new NotFoundException(MessageUtils.get(language, "msg.http-status.product.amount"));
                } else {
                    detailCartEntity.setAmount(amount);
                }
            } else {

                if (amount + detailCart.getAmount() > productEntity.getQuantity()) {
                    throw new NotFoundException(MessageUtils.get(language, "msg.http-status.product.amount"));
                } else {
                    detailCartEntity.setAmount(amount + detailCart.getAmount());
                }
                detailCartEntity.setCartId(cartId);
                detailCartEntity.setId(detailCart.getId());
            }
        }

        DetailCartEntity cartDetail = this.detailCartRepo.save(detailCartEntity);
        response  = this.modelMapper.map(cartDetail, DetailCartResponse.class);

        response.setPriceSale(request.getPriceSale());
        response.setProductDiscounts(request.getProductDiscounts());
        response.setTypeOder(request.getTypeOrder());
        log.info("amount product invalid : End");
        return response;
    }

    @Override
    public DetailCartResponse update(Integer id, int amount) {
        log.info("Update to cart service : Start");

        DetailCartEntity cartDetailEntity = this.detailCartRepo.findById(id).orElseThrow(() -> new NotFoundException("CartDetail Not Found"));
        ProductEntity productEntity = this.productRepo.findByIdAndStatus(cartDetailEntity.getProductId(), status);

        if (amount < 0) {
            throw new NotFoundException("amount < 0");
        } else if (amount == 0) {
            detailCartRepo.delete(cartDetailEntity);
        }else {
            cartDetailEntity.setAmount(amount);
        }
        cartDetailEntity = this.detailCartRepo.save(cartDetailEntity);

        log.info("Update to cart : End");
        return this.modelMapper.map(cartDetailEntity, DetailCartResponse.class);
    }

    @Override
    public DetailCartResponse delete(Integer id) {
        log.info("Delete to cart service : Start");

        DetailCartResponse response = new DetailCartResponse();
        DetailCartEntity cartDetailEntity = this.detailCartRepo.findById(id).orElseThrow(() -> new NotFoundException("CartDetail Not Found"));
        detailCartRepo.delete(cartDetailEntity);

        response.setAmount(cartDetailEntity.getId());
        response.setProductId(cartDetailEntity.getProductId());

        log.info("Delete to cart : End");
        return response;
    }

    @Override
    public DetailCartResponse deleteAllCart(String userName) {
        log.info("Delete all cart service : Start");

        DetailCartResponse response = new DetailCartResponse();
        CartEntity cartEntity = this.cartRepo.findByUserName(userName).orElseThrow(() -> new NotFoundException("Cart not found"));
        List<DetailCartEntity> cartDetailEntities = this.detailCartRepo.findAllByCartId(cartEntity.getId());
        this.detailCartRepo.deleteAll(cartDetailEntities);

        log.info("Delete all cart : End");
        return response;
    }

    @Override
    public DetailCartResponse deleteListCart(List<Integer> id) {
        log.info("Delete list cart service : Start");
        DetailCartResponse response = new DetailCartResponse();

        for (Integer items : id) {
            DetailCartEntity cartDetailEntity = this.detailCartRepo.findById(items).orElseThrow(() -> new NotFoundException("CartDetail Not Found"));
            detailCartRepo.delete(cartDetailEntity);
        }
        log.info("Delete list cart service : End");
        return response;
    }

    @Override
    public CartResponse findById(String userName) {

        log.info("find by id of list cart service : Start");
        CartResponse response = new CartResponse();
        List<DetailCartItems> cartDetailResponseList = new ArrayList<>();

        AccountEntity accountEntity = this.accountRepo.findByUserNameAndStatus(userName, 1);
        if(accountEntity == null){
            throw new NotFoundException("Account not found");
        }
        response.setUserName(accountEntity.getUserName());

        CartEntity cartEntity = this.cartRepo.findByUserName(userName).orElseThrow(() -> new NotFoundException("Cart not found"));
        response.setCartId(cartEntity.getId());

        List<DetailCartEntity> cartDetailEntityList = this.detailCartRepo.findAllByCartId(cartEntity.getId());

        if (cartDetailEntityList.isEmpty()) {
            throw new NotFoundException("Cart detail null");
        }
        for (DetailCartEntity detailCart : cartDetailEntityList) {
            DetailCartItems detailCartItems = new DetailCartItems();

            ProductEntity productEntity = this.productRepo.findById(detailCart.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
            detailCartItems.setProductId(productEntity.getId());
            detailCartItems.setProductName(productEntity.getFigure());
            detailCartItems.setPrice(productEntity.getPrice());
            detailCartItems.setId(detailCart.getId());
            detailCartItems.setPriceSales(productEntity.getPriceSales());
            detailCartItems.setTypeOrder(productEntity.getTitle());
            detailCartItems.setAmountProduct(productEntity.getQuantity());
            detailCartItems.setHeight(productEntity.getHeight());
            detailCartItems.setWeight(productEntity.getWeight());

            List<ImageEntity> imageEntityList = this.imageRepo.findByProductID(productEntity.getId());
            if(imageEntityList == null){
                throw new NotFoundException("Img null");
            }else {
                for (ImageEntity img : imageEntityList) {
                    List<ImgProductItems> itemsList = new ArrayList<>();
                    ImgProductItems items = this.modelMapper.map(img, ImgProductItems.class);
                    itemsList.add(items);
                    detailCartItems.setProductImg(itemsList);
                    detailCartItems.setUrl(itemsList.get(0).getUrl());
                }
            }
            detailCartItems.setAmount(detailCart.getAmount());

            cartDetailResponseList.add(detailCartItems);
        }
        response.setItems(cartDetailResponseList);
        response.setAmountProduct(cartDetailEntityList.size());
        log.info("find by id of list cart service : End");
        return response;
    }

    @Override
    public Page<CartResponse> findAll(PageItem items) {
        return null;
    }

    @Override
    public Long count(String userName) {
        CartEntity cartEntity = this.cartRepo.findByUserName(userName).orElseThrow(() -> new NotFoundException("Cart not found"));
        return this.detailCartRepo.countByCartId(cartEntity.getId());
    }

}
