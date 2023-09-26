package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.ProductItems;
import com.qlmh.datn_qlmh.dtos.request.ProductReq;
import com.qlmh.datn_qlmh.dtos.response.ProductResp;
import com.qlmh.datn_qlmh.dtos.response.ProductResponse;
import com.qlmh.datn_qlmh.entities.*;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.ProductService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.Common;
import com.qlmh.datn_qlmh.utilities.ConvertStatus;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Mapper mapper;
    private final ModelMapper modelMapper;
    private final ProductRepo productRepo;
    private final ImageRepo imageRepo;
    private final ProductDiscountRepo productDiscountRepo;
    private final DiscountRepo discountRepo;
    private final CategoriesRepo categoriesRepo;
    private final ManufacturerRepo manufacturerRepo;
    private final SeriesRepo seriesRepo;
    private final HistoryPriceRepo historyPriceRepo;

    @Autowired
    public ProductServiceImpl(Mapper mapper, ModelMapper modelMapper, ProductRepo productRepo, ImageRepo imageRepo, ProductDiscountRepo productDiscountRepo, DiscountRepo discountRepo, CategoriesRepo categoriesRepo, ManufacturerRepo manufacturerRepo, SeriesRepo seriesRepo, HistoryPriceRepo historyPriceRepo) {
        this.mapper = mapper;
        this.modelMapper = modelMapper;
        this.productRepo = productRepo;
        this.imageRepo = imageRepo;
        this.productDiscountRepo = productDiscountRepo;
        this.discountRepo = discountRepo;
        this.categoriesRepo = categoriesRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.seriesRepo = seriesRepo;
        this.historyPriceRepo = historyPriceRepo;
    }


    @Override
    public List<ProductResp> getAllProducts() {

        List<ProductResp> list = productRepo.findAll().stream().map((entity) -> mapper.toProductResp(entity)).collect(Collectors.toList());
        for (ProductResp productResp : list) {
            List<ImageEntity> listimgs = imageRepo.getImageByProductID(productResp.getId());
            productResp.setListImage(listimgs);
        }
        return list;
    }

    @Override
    public List<ProductResp> getProductByTitle(Integer title) {

        return productRepo.getProductsByTitle(title).stream().map((entity) -> mapper.toProductResp(entity)).collect(Collectors.toList());
    }

    @Override
    public ProductReq create(ProductReq product) {
        ProductEntity productEntity = mapper.toProductEntity(product);
        productEntity.setStatus(1);
        productEntity = productRepo.save(productEntity);

        HistoryPriceEntity historyPriceEntity = new HistoryPriceEntity();
        historyPriceEntity.setPrice(productEntity.getPriceNew());
        historyPriceEntity.setIdProduct(productEntity.getId());
        historyPriceRepo.save(historyPriceEntity);

        return mapper.toProductReq(productEntity);
    }

    @Override
    public ProductReq update(ProductReq product) {
        ProductEntity productEntity = this.productRepo.findById(product.getId()).orElseThrow(() -> new NotFoundException("Product not found : "));
        if(productEntity == null){
            throw  new NotFoundException("Product not found :");
        }
        productEntity = mapper.toProductEntity(product);
        productEntity = productRepo.save(productEntity);
        return mapper.toProductReq(productEntity);
    }
    @Override
    public ProductReq updateStatus(Integer id, int status) {
        ProductEntity productEntity = this.productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found : "));
        if(productEntity == null){
            throw  new NotFoundException("Product not found :");
        }
        productEntity.setStatus(status);
        productEntity.setId(id);
        productEntity = productRepo.save(productEntity);
        return mapper.toProductReq(productEntity);
    }
    @Override
    public ProductResp getByID(Integer id) {
        ProductEntity productEntity = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found : " + id));
        ProductResp productResp = mapper.toProductResp(productEntity);
        List<ImageEntity> listimgs = imageRepo.getImageByProductID(productResp.getId());
        productResp.setListImage(listimgs);

        return productResp;
    }

    @Override
    public PageData<ProductResponse> getAll(ProductItems request, int pageNum, int pageSize,  String sortBy, String sortDirection) {
        List<Integer> categories = request.getCategoryIds();
        List<String> ratio = request.getRatio();
        List<Double> minPrice = request.getMinPrice();
        List<Double> maxPrice = request.getMaxPrice();
        List<Integer> seriesId = request.getSeriesID();
        Integer status = request.getAvailable();
        String name = request.getName();
        List<Integer> manufacturerID = request.getManufacturerID();
        Boolean statusCode = true;
        Integer title = request.getTitle();

        Specification<ProductEntity> receptionistSpecification = (root, cq, cb) -> {
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, name, "figure"),
                    QueryUtils.buildFilter(root, cb,"manufacturerID", manufacturerID),
                    QueryUtils.buildFilter(root, cb,"categoryId", categories),
                    QueryUtils.buildFilter(root, cb,"seriesID", seriesId),
                    QueryUtils.buildFilter(root, cb,"ratio", ratio),
                    QueryUtils.buildBetweenFilter(root, cb,"priceSales", minPrice, maxPrice),
                    QueryUtils.buildEqFilter(root, cb, "status", status)
                    );
        };
        Sort sort;
        if(sortBy == null && sortDirection == null){
            sort = QueryUtils.buildSort("createDate", "desc");
        }
        else {
            sort= QueryUtils.buildSort(sortBy, sortDirection);
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<ProductEntity> page = productRepo.findAll(receptionistSpecification, pageable);

        List<ProductEntity> productEntities = page.toList();
        List<DiscountEntity> discountEntities = discountRepo.getDiscountActive(Sort.by(Sort.Direction.DESC,"discountStart"),1,new Date());
        productEntities= productEntities.stream().map((i)->{i.setPriceSales(i.getPriceNew());return i;}).collect(Collectors.toList());

        for (ProductEntity pi: productEntities) {
        for (DiscountEntity item: discountEntities) {
                if(productDiscountRepo.getByDiscountIdAndProductId(item.getId(),pi.getId())>0L ){
                    if(item.getDiscountType()== Constant.TypeDiscount.PERCENT){
                        // xu ly cong tong hay cong don
                        BigDecimal priceSale = pi.getPriceNew().multiply(item.getDiscountAmount().divide(BigDecimal.valueOf(100)));
                        pi.setPriceSales(pi.getPriceSales().subtract(priceSale));
                    }else{
                        pi.setPriceSales(pi.getPriceSales().subtract(item.getDiscountAmount()));
                    }
                    if(pi.getPriceSales().compareTo(BigDecimal.ZERO) < 0){
                        pi.setPriceSales(BigDecimal.ZERO);
                    }
                }
            }
            this.productRepo.save(pi);
        }
        productEntities.stream().forEach((item)->{System.out.println(item);});
        if (page.getTotalElements() > 0) {
            List<ProductResponse> productResponses = new ArrayList<>();

            for (ProductEntity productEntity : productEntities) {
                ProductResponse productResponse = modelMapper.map(productEntity, ProductResponse.class);

                List<ProductDiscountEntity> findByProductIdAndStatus = this.productDiscountRepo.findByProductIdAndStatus(productEntity.getId(), statusCode);
                List<ImageEntity> images = imageRepo.findByProductID(productEntity.getId());
                ImageEntity image = new ImageEntity();

                if (images.size() <=1) {
                    for (int i = 0; i <= 2 ; i++) {
                        image.setUrl("1680523065800e5fed3cb-1207-4e65-bd46-5ed03297e319No-Image-Placeholder.svg.png");
                        image.setProductID(productEntity.getId());
                        image =this.imageRepo.save(image);
                    }
                    images.add(image);

                }
                productResponse.setProductDiscounts(findByProductIdAndStatus);
                productResponse.setListImage(images);

                CategoriesEntity findById = this.categoriesRepo.findById(productEntity.getCategoryId()).orElseThrow(() -> new NotFoundException("category not found"));
                productResponse.setCategoryName(findById.getName());

                ManufacturerEntity findManufacturerEntity = this.manufacturerRepo.findById(productEntity.getManufacturerID()).orElseThrow(() -> new NotFoundException("Manufacturer not found"));
                productResponse.setManufacturerName(findManufacturerEntity.getName());

                SeriesEntity findBySeries = this.seriesRepo.findById(productEntity.getSeriesID()).orElseThrow(() -> new NotFoundException("Series not found"));
                productResponse.setSeriesName(findBySeries.getName());
                productResponse.setAvailableName(ConvertStatus.getProductStatusName(productEntity.getStatus(), "vn"));

                productResponses.add(productResponse);
            }
            return PageData.of(page, productResponses);
        }
        throw new NotFoundException("Product not exist");
    }
    @Override
    public ProductResponse detailProduct(Integer id) {
        Boolean statusCode = true;

        ProductEntity productEntity = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductResponse productResponse = modelMapper.map(productEntity, ProductResponse.class);

        List<ProductDiscountEntity> findByProductIdAndStatus = this.productDiscountRepo.findByProductIdAndStatus(productEntity.getId(), statusCode);

        productResponse.setPriceSales(productEntity.getPriceSales());
        List<ImageEntity> images = imageRepo.getImageByProductID(productEntity.getId());
        if (images == null) {
            productResponse.setListImage(null);
        }
        productResponse.setProductDiscounts(findByProductIdAndStatus);
        productResponse.setListImage(images);
        productResponse.setAvailableName(ConvertStatus.getProductStatusName(productEntity.getStatus(), "vn"));

        CategoriesEntity findById = this.categoriesRepo.findById(productEntity.getCategoryId()).orElseThrow(() -> new NotFoundException("category not found"));
        productResponse.setCategoryName(findById.getName());

        ManufacturerEntity findManufacturerEntity = this.manufacturerRepo.findById(productEntity.getManufacturerID()).orElseThrow(() -> new NotFoundException("Manufacturer not found"));
        productResponse.setManufacturerName(findManufacturerEntity.getName());

        SeriesEntity findBySeries = this.seriesRepo.findById(productEntity.getSeriesID()).orElseThrow(() -> new NotFoundException("category not found"));
        productResponse.setSeriesName(findBySeries.getName());

        return productResponse;
    }

    public static Specification<ProductEntity> searchByCriteria(List<Integer> categoryIds, List<String> ratio, List<Double> minPrice, List<Double> maxPrice, List<Integer> manufacturerID, List<Integer> seriesID, String name, Boolean available, Integer title) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty()) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("figure")), "%" + name.toUpperCase() + "%");
                predicates.add(namePredicate);
            }
            if (manufacturerID != null && !manufacturerID.isEmpty() ) {
                Predicate manufacturerPredicate = root.get("manufacturerID").in(manufacturerID);
                predicates.add(manufacturerPredicate);
            }
            if (categoryIds != null && !categoryIds.isEmpty()) {
                Predicate categoryPredicate = root.get("categoryId").in(categoryIds);
                predicates.add(categoryPredicate);
            }
            if (seriesID != null && !seriesID.isEmpty() ) {
                Predicate seriesIDPredicate = root.get("seriesID").in(seriesID);
                predicates.add(seriesIDPredicate);
            }
            if (ratio != null && !ratio.isEmpty()) {
                Predicate ratios = root.get("ratio").in(ratio);
                predicates.add(ratios);
            }
            if (available != null) {
                Predicate statusPredicate = criteriaBuilder.equal(root.get("available"), available);
                predicates.add(statusPredicate);
            }
            if (title != null) {
                Predicate titlePredicate = criteriaBuilder.equal(root.get("title"), title);
                predicates.add(titlePredicate);
            }
            if (minPrice != null && maxPrice != null && minPrice.size() == maxPrice.size()) {
                List<Predicate> pricePredicates = new ArrayList<>();
                for (int i = 0; i < minPrice.size(); i++) {
                    Predicate pricePredicate = criteriaBuilder.between(root.get("priceSales"), minPrice.get(i), maxPrice.get(i));
                    pricePredicates.add(pricePredicate);
                }
                predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[pricePredicates.size()])));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            return finalPredicate;
        };
    }

}
