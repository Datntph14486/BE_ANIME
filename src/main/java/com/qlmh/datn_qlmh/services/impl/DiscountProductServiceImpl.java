package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.dtos.request.ProductDiscountDto;
import com.qlmh.datn_qlmh.dtos.request.ProductForDiscountDto;
import com.qlmh.datn_qlmh.entities.*;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.DiscountPlusService;
import com.qlmh.datn_qlmh.services.DiscountProductService;
import com.qlmh.datn_qlmh.services.DiscountService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountProductServiceImpl implements DiscountProductService {
    private final DiscountService discountService;
    private final DiscountPlusService discountPlusService;
    private final CategoriesRepo categoriesRepo;
    private final ManufacturerRepo manufacturerRepo;
    private final ProductDiscountRepo productDiscountRepo;
    private final SeriesRepo seriesRepo;
    private final ProductRepo productRepo;
    private final Mapper mapper;

    @Override
    public ProductDiscountDto save(ProductDiscountDto productDiscountDto) {
        DiscountReq discountReq = discountService.findById(productDiscountDto.getDiscountId());
        ProductEntity productEntity = productRepo.findById(productDiscountDto.getProduct().getId()).orElseThrow(()->new NotFoundException("Product not found : "+productDiscountDto.getProduct().getId()) );
        ProductDiscountEntity productDiscountEntity;
        if (productDiscountDto.getId() != null) {
            productDiscountRepo.findById(productDiscountDto.getId()).orElseThrow(() -> new NotFoundException("Product discount not found : " + productDiscountDto.getId()));
        }
        productDiscountDto.setStatus(true);
        productDiscountEntity = productDiscountRepo.save(new ProductDiscountEntity(productDiscountDto));

        ProductForDiscountDto productForDiscountDto = new ProductForDiscountDto(productEntity);
        productForDiscountDto.setPriceSale(this.calculatePriceSale(discountReq,productForDiscountDto));
        ProductDiscountDto p = new ProductDiscountDto(productDiscountEntity);
        p.setProduct(productDiscountDto.getProduct());
        if(productDiscountDto.getDiscountPlus()!= null){
            p.setDiscountPlus(productDiscountDto.getDiscountPlus());
        }
        return p;
    }

    @Override
    public ProductDiscountDto remove(ProductDiscountDto productDiscountDto) {
        DiscountReq discountReq = discountService.findById(productDiscountDto.getDiscountId());
        ProductEntity productEntity = productRepo.findById(productDiscountDto.getProduct().getId()).orElseThrow(()->new NotFoundException("Product not found : "+productDiscountDto.getProduct().getId()) );
        ProductDiscountEntity productDiscountEntity;
        if (productDiscountDto.getId() != null) {
            productDiscountRepo.findById(productDiscountDto.getId()).orElseThrow(() -> new NotFoundException("Product discount not found : " + productDiscountDto.getId()));
        }
        productDiscountDto.setStatus(false);
        productDiscountEntity = productDiscountRepo.save(new ProductDiscountEntity(productDiscountDto));

        ProductForDiscountDto productForDiscountDto = new ProductForDiscountDto(productEntity);
        productForDiscountDto.setPriceSale(this.calculatePriceSale(discountReq,productForDiscountDto));
        ProductDiscountDto p = new ProductDiscountDto(productDiscountEntity);
        if(productDiscountDto.getDiscountPlus()!= null){
            p.setDiscountPlus(productDiscountDto.getDiscountPlus());
        }
        p.setProduct(productDiscountDto.getProduct());
        return p;
    }

    @Override
    public List<ProductDiscountDto> saveAll(List<ProductDiscountDto> productDiscountDto) {
        return productDiscountDto.stream().map((i)-> this.save(i)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDiscountDto> removeAll(List<ProductDiscountDto> productDiscountDto) {
        return productDiscountDto.stream().map((i)-> this.remove(i)).collect(Collectors.toList());
    }

    @Override
    public PageData<ProductDiscountDto> getById(Long id, Pageable pageable, Integer[] category, String name) {
        DiscountReq discountReq = discountService.findById(id);
        Specification<ProductEntity> productEntitySpecification = (root, query, criteriaBuilder) -> {
            Predicate namePredicate =
//                    criteriaBuilder.equal(root.get("figure"),name);
            QueryUtils.buildLikeFilter(root,criteriaBuilder,name,"figure");
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"),1);
            Predicate categoriesPredicate =
//                    root.get("categoryId").in(category);
            QueryUtils.buildFilter(root,criteriaBuilder,"categoryId", Arrays.stream(category).toList());
          return  criteriaBuilder.and(namePredicate,statusPredicate,categoriesPredicate);
        };
        Page<ProductEntity> p = productRepo.findAll(productEntitySpecification,pageable);
        System.out.println("size : "+p.toList().size());
        List<ProductDiscountEntity> productDiscountEntities = productDiscountRepo.getByDiscountId(id);
        List<ProductDiscountDto> l =p.toList().stream().map((i)->new ProductDiscountDto(null,null,this.getV2(i,discountReq),id,null)).collect(Collectors.toList());
        for (int i = 0; i < l.size(); i++) {
            for(ProductDiscountEntity item:productDiscountEntities){
                if((long)l.get(i).getProduct().getId()== item.getProductId()){
                    l.get(i).setId(item.getId());
                    l.get(i).setStatus(item.getStatus());
                    l.get(i).setDiscountPlus(discountPlusService.findByDiscountProductId(item.getId()));
                }
            }
        }
        return PageData.of(p,l);
//        return new DiscountProductDto(discountReq,PageData.of(p,p.toList().stream().map((i)->this.get(i,discountReq)).collect(Collectors.toList())));
    }
    @Deprecated()
    private ProductForDiscountDto get(ProductEntity product,DiscountReq discountReq){
        ProductForDiscountDto productForDiscountDto = new ProductForDiscountDto(product);
        BigDecimal priceSale = this.calculatePriceSale(discountReq,productForDiscountDto);
        productForDiscountDto.setPriceSale(priceSale);
        productForDiscountDto.setCategoryId(mapper.toCategoriesReq(categoriesRepo.findById(product.getCategoryId()).orElseThrow(()->new NotFoundException("Category not found"))));
        productForDiscountDto.setManufacturerID(mapper.toManufacturerReq(manufacturerRepo.findById(product.getManufacturerID()).orElseThrow(()->new NotFoundException("Manufacturer not found"))));
        productForDiscountDto.setSeriesID(mapper.toSeriesReq(seriesRepo.findById(product.getSeriesID()).orElseThrow(()->new NotFoundException("Series not found"))));
        return productForDiscountDto;
    }
    private ProductForDiscountDto getV2(ProductEntity product,DiscountReq discountReq){
        ProductForDiscountDto productForDiscountDto = new ProductForDiscountDto(product);
        BigDecimal priceSale = this.calculatePriceSale(discountReq,productForDiscountDto);
        productForDiscountDto.setPriceSale(priceSale);
        List<Object[]> objects = productRepo.getCustom(product.getId());
        for (Object[] obs : objects){
            for (Object ob:obs) {
                if(ob instanceof SeriesEntity){
                    productForDiscountDto.setSeriesID(mapper.toSeriesReq((SeriesEntity) ob));
                }
                else if(ob instanceof CategoriesEntity){
                    productForDiscountDto.setCategoryId(mapper.toCategoriesReq((CategoriesEntity) ob));
                }
                else if(ob instanceof ManufacturerEntity){
                    productForDiscountDto.setManufacturerID(mapper.toManufacturerReq((ManufacturerEntity) ob));
                }
            }
        }
        return productForDiscountDto;
    }
    private BigDecimal calculatePriceSale(DiscountReq discountReq, ProductForDiscountDto productForDiscountDto){

        BigDecimal priceSale;
        if(discountReq.getDiscountType() == Constant.TypeDiscount.PERCENT){
            BigDecimal sale = productForDiscountDto.getPrice().multiply(discountReq.getDiscountAmount().multiply(BigDecimal.valueOf(0.01)));
            priceSale = productForDiscountDto.getPrice().subtract(sale);
        }
        else{
            priceSale = productForDiscountDto.getPrice().subtract(discountReq.getDiscountAmount());
            if(priceSale.compareTo(BigDecimal.ZERO) < 0){
                priceSale= BigDecimal.ZERO;
            }
        }
        return priceSale;
    }
}
