package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.configs.Config;
import com.qlmh.datn_qlmh.configs.RegisterForThymeleaf;
import com.qlmh.datn_qlmh.configs.mail.MailService;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.ExportPdfDTO;
import com.qlmh.datn_qlmh.dtos.Items.SearchFilterPage;
import com.qlmh.datn_qlmh.dtos.request.*;
import com.qlmh.datn_qlmh.dtos.request.BillRequest;
import com.qlmh.datn_qlmh.dtos.request.BillRequestv2;
import com.qlmh.datn_qlmh.dtos.response.*;
import com.qlmh.datn_qlmh.entities.*;
import com.qlmh.datn_qlmh.exceptions.BadRequestException;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.redis.DetailBillRedis;
import com.qlmh.datn_qlmh.redis.repo.DetailBillRedisRepo;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.ultil.ExportPDFUtils;
import com.qlmh.datn_qlmh.utilities.Common;
import com.qlmh.datn_qlmh.utilities.ConvertStatus;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
public class BillServiceImpl {
    private final AccountRepo accountRepo;
    private final ProductRepo productRepo;
    private final BillRepo billRepo;
    private final DetailBillRepo detailBillRepo;
    private final VoucherRepo voucherRepo;
    private final DiscountRepo discountRepo;
    private final VoucherAccountRepo voucherAccountRepo;
    private final ProductDiscountRepo productDiscountRepo;
    private final ProductDiscountBillRepo productDiscountBillRepo;
    private final DiscountWithBillRepo discountWithBillRepo;
    private final PreOderEntityRepo preOderEntityRepo;
    private final ModelMapper modelMapper;
    private final DeliveryInvoiceRepo deliveryInvoiceRepo;
    private final CategoriesRepo categoriesRepo;
    private final ImageRepo imageRepo;
    private final RateRepo rateRepo;
    private final MailService mailService;
    private final RestTemplate restTemplate;
    private final Mapper voucherMapper;
    private static int status =1;
    private final DetailBillRedisRepo detailBillRedisRepo;

    @Autowired
    public BillServiceImpl(AccountRepo accountRepo, ProductRepo productRepo, BillRepo billRepo,
                           DetailBillRepo detailBillRepo, VoucherRepo voucherRepo, DiscountRepo discountRepo,
                           VoucherAccountRepo voucherAccountRepo, ProductDiscountRepo productDiscountRepo,
                           ProductDiscountBillRepo productDiscountBillRepo, DiscountWithBillRepo discountWithBillRepo,
                           PreOderEntityRepo preOderEntityRepo, ModelMapper modelMapper, DeliveryInvoiceRepo deliveryInvoiceRepo,
                           CategoriesRepo categoriesRepo, ImageRepo imageRepo, RateRepo rateRepo, MailService mailService, RestTemplate restTemplate, Mapper voucherMapper, DetailBillRedisRepo detailBillRedisRepo) {
        this.accountRepo = accountRepo;
        this.productRepo = productRepo;
        this.billRepo = billRepo;
        this.detailBillRepo = detailBillRepo;
        this.voucherRepo = voucherRepo;
        this.discountRepo = discountRepo;
        this.voucherAccountRepo = voucherAccountRepo;
        this.productDiscountRepo = productDiscountRepo;
        this.productDiscountBillRepo = productDiscountBillRepo;
        this.discountWithBillRepo = discountWithBillRepo;
        this.preOderEntityRepo = preOderEntityRepo;
        this.modelMapper = modelMapper;
        this.deliveryInvoiceRepo = deliveryInvoiceRepo;
        this.categoriesRepo = categoriesRepo;
        this.imageRepo = imageRepo;
        this.rateRepo = rateRepo;
        this.mailService = mailService;
        this.restTemplate = restTemplate;
        this.voucherMapper = voucherMapper;
        this.detailBillRedisRepo = detailBillRedisRepo;
    }

    public BillRequest saveOrder(BillRequest billRequest) {
        Boolean statusCode = true;
        SimpleDateFormat format = new SimpleDateFormat("yyMdhms");
        String code = format.format(new Date());
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        Date newDate = calendar.getTime();
        Map<String, Object> map = new HashMap<>();

        BillEntity billEntity = new BillEntity();
        if (accountRepo.findById(billRequest.getUsername()).isEmpty()) {
            throw new NotFoundException("User not exist");
        }

        billRequest.getDetailBillRequests().forEach(orderDetailRq -> {
            ProductEntity productEntity = Optional.ofNullable(this.productRepo.findByIdAndStatus(orderDetailRq.getProductId(), status)).orElseThrow(() -> new NotFoundException("CartDetail Not Found"));

            if (productEntity.getQuantity() < orderDetailRq.getAmount()) {
                throw new InvalidArgumentException("số lượng không đủ");
            }
        });

        BeanUtils.copyProperties(billRequest, billEntity);
        billEntity.setCodeBill("HD" + code);
        billEntity.setTypeOfBill(0);
        billEntity.setStatus(BillEntity.StatusEnum.CHO_XAC_NHAN);

        BillEntity bill = this.billRepo.save(billEntity);
        BeanUtils.copyProperties(bill, billRequest);

        List<DetailBillEntity> detailBillEntityList = new ArrayList<>();
        BillResponse.DetailBillResponse detailBillResponse = new BillResponse.DetailBillResponse();

        for (BillRequest.DetailBillRequest detailBillRq : billRequest.getDetailBillRequests()) {
            ProductEntity productEntity = Optional.ofNullable(this.productRepo.findByIdAndStatus(detailBillRq.getProductId(), status)).orElseThrow(() -> new NotFoundException("Product Not Found"));
            if (productEntity.getQuantity() < detailBillRq.getAmount()) {
                throw new InvalidArgumentException("số lượng không đủ");
            }
            BigDecimal priceSalesProduct = detailBillRq.getPriceSale();
            DetailBillEntity detailBill = new DetailBillEntity();
            BeanUtils.copyProperties(detailBillRq, detailBill);
            detailBill.setPriceSale(priceSalesProduct);
            detailBill.setBillId(bill.getId());
            detailBill.setTypeOfBill(0);
            detailBillEntityList.add(detailBill);
            BeanUtils.copyProperties(detailBill, detailBillResponse);
        }
        this.detailBillRepo.saveAll(detailBillEntityList);
        for (DetailBillEntity detailBillEntity : detailBillEntityList) {
            ProductEntity productEntity = Optional.ofNullable(this.productRepo.findByIdAndStatus(detailBillEntity.getProductId(), status)).orElseThrow(() -> new NotFoundException("Product Not Found"));
            productEntity.setQuantity(productEntity.getQuantity() - detailBillEntity.getAmount());
            this.productRepo.save(productEntity);

            List<ProductDiscountEntity> productDiscountEntities = this.productDiscountRepo.findByProductIdAndStatus(productEntity.getId(), true);
            for (ProductDiscountEntity productDiscountEntity : productDiscountEntities) {
                ProductDiscountBillEntity productDiscountBillEntity = new ProductDiscountBillEntity();
                productDiscountBillEntity.setProductDiscountId(productDiscountEntity.getId());
                productDiscountBillEntity.setBillDetailId(detailBillEntity.getId());
                this.productDiscountBillRepo.save(productDiscountBillEntity);
            }
        }
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromHttpUrl(Config.vnp_ReturnUrl);
        URI newUri = builder.build().toUri();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        map.put("code", bill.getCodeBill());
        map.put("name", bill.getFullName());
        map.put("total", bill.getTotal());
        map.put("address", bill.getAddress());
        map.put("date", formatter.format(newDate));
        map.put("dateTime", formatter.format(bill.getCreateDate()));
        map.put("url", newUri.toString());
        RegisterForThymeleaf billThym = new RegisterForThymeleaf(map);
        billThym.setPath("mailbill_");
        try {
            mailService.queue(billRequest.getEmail(), "Xác nhận thanh toán đơn hàng", "", billThym);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return billRequest;
    }

    public Double voucher(String voucherCode, String username, BigDecimal total) {
        double totalAmountVoucherWithBill = 0;
        double percentVoucherWithBill = 0;
        double totalAfterPercent = 0;

        VoucherAccountEntity voucherAccountEntity = this.voucherAccountRepo.findByCodeAndUsername(voucherCode, username);
        if (voucherAccountEntity != null) {
            VoucherEntity voucherEntity = this.voucherRepo.findById(voucherAccountEntity.getVoucherId()).orElseThrow(() -> new NotFoundException("Voucher Not Found"));

            if (Common.isValid(voucherEntity.getDiscountStart(), voucherEntity.getDiscountEnd())) {

                if (voucherEntity.getDiscountType().byteValue() == 1) {
                    totalAmountVoucherWithBill += voucherEntity.getDiscountAmount().doubleValue();
                }
                if (voucherEntity.getDiscountType().byteValue() == 0) {
                    if (total.doubleValue() > voucherEntity.getMinPrice().doubleValue()) {
                        totalAfterPercent = total.doubleValue() * (voucherEntity.getDiscountAmount().doubleValue() / 100);
                        if (totalAfterPercent > voucherEntity.getMaxPrice().doubleValue()) {
                            totalAfterPercent = voucherEntity.getMaxPrice().doubleValue();
                        }
                    }
                }
            }
        }
        return totalAfterPercent;
    }

    public void update(String code, String username, String status) {
        Boolean disable;
        VoucherAccountEntity voucherAccountEntity = this.voucherAccountRepo.findByCodeAndUsername(code, username);
        voucherAccountEntity.setId(voucherAccountEntity.getId());
        if(status == null){
            voucherAccountEntity.setStatus(null);
        }else if(status.equals("true")){
            voucherAccountEntity.setStatus(true);
        }else {
            voucherAccountEntity.setStatus(false);
        }
        this.voucherAccountRepo.save(voucherAccountEntity);
    }

    public BillRequest.DetailBillRequest updateWaitOrder(Integer idBill,String username, BillRequest.DetailBillRequest billRequest) {
        log.info("Add to bill service :: Start");
        if (idBill == null || idBill.equals("") || username == null) throw new InvalidArgumentException("id is not null ");
        if (accountRepo.findById(username).isEmpty()) {
            throw new NotFoundException("User not exist");
        }
        BillEntity billEntityRequest = billRepo.findById(idBill).orElseThrow(() -> new NotFoundException("Order không tồn tại"));

        DetailBillEntity detailCartEntity = new DetailBillEntity();
        BeanUtils.copyProperties(billRequest, detailCartEntity);

        ProductEntity productEntity = this.productRepo.findByIdAndStatus(billRequest.getProductId(), status);
        if (ObjectUtils.isEmpty(productEntity)) {
            throw new NotFoundException("Product không tồn tại");

        } else {
            if (productEntity.getQuantity() <= 0) {
                throw new BadRequestException("Số lượng <=0");
            }
            detailCartEntity.setProductId(productEntity.getId());

            DetailBillEntity detailCart = this.detailBillRepo.findByBillIdAndProductId(billEntityRequest.getId(), productEntity.getId());
            if (ObjectUtils.isEmpty(detailCart)) {
                if (detailCart != null) {
                    if (Objects.equals(detailCart.getAmount(), productEntity.getQuantity())) {
                        throw new BadRequestException("Số lượng không hợp lệ");
                    }
                }
                detailCartEntity.setBillId(billEntityRequest.getId());
                if (billRequest.getAmount() > productEntity.getQuantity()) {
                    throw new BadRequestException("Số lượng không hợp lệ");
                } else {
                    detailCartEntity.setAmount(billRequest.getAmount());
                }
            } else {

                if (billRequest.getAmount() + detailCart.getAmount() > productEntity.getQuantity()) {
                    throw new BadRequestException("Số lượng không hợp lệ");
                } else {
                    detailCartEntity.setAmount(billRequest.getAmount() + detailCart.getAmount());
                }
                detailCartEntity.setBillId(billEntityRequest.getId());
                detailCartEntity.setId(detailCart.getId());
            }
        }

        DetailBillEntity cartDetail = this.detailBillRepo.save(detailCartEntity);

        productEntity.setQuantity(productEntity.getQuantity() - billRequest.getAmount());
        this.productRepo.save(productEntity);
        BillRequest.DetailBillRequest response  = this.modelMapper.map(cartDetail, BillRequest.DetailBillRequest.class);
        log.info("Bill- service : End");
        return response;

    }

    public void delete(Integer id, int amount, Integer product){
        if (id == null || id.equals("") ||product == null || product.equals("")) throw new InvalidArgumentException("id is not null ");
        ProductEntity productEntity = this.productRepo.findByIdAndStatus(product, 1);
        if(productEntity == null){
            throw new NotFoundException("Product không tồn tại");
        }
        DetailBillEntity detailBillEntity = this.detailBillRepo.findById(id).orElseThrow(() -> new NotFoundException("Order không tồn tại"));

        DetailBillEntity detailCart = this.detailBillRepo.findByIdAndProductId(detailBillEntity.getId(), productEntity.getId());

        productEntity.setQuantity(amount + productEntity.getQuantity());
        this.productRepo.save(productEntity);
        this.detailBillRepo.delete(detailCart);
    }
    public BillResponse updateBill(BillRequest request){
        if (request.getId() == null ) throw new InvalidArgumentException("id is not null ");
        if (request.getUsername() == null) throw new InvalidArgumentException("username is not null ");

        this.accountRepo.findById(request.getUsername()).orElseThrow(()-> new NotFoundException("Account not exist"));
        BillEntity billEntity = this.billRepo.findById(request.getId()).orElseThrow(()-> new NotFoundException("Bill not found"));

        billEntity.setAddress(request.getAddress());
        billEntity.setFullName(request.getFullName());
        billEntity.setUsername(request.getUsername());
        billEntity.setPhoneNumber(request.getPhoneNumber());
        billEntity.setTotal(request.getTotal());
        billEntity.setTransportFee(request.getTransportFee());
        billEntity.setAddressCode(request.getAddressCode());

        this.billRepo.save(billEntity);
        return  this.modelMapper.map(billEntity, BillResponse.class);
    }
    public BillResponse.DetailBillResponse updateAmount(Integer id, Integer productId, Integer amountNew, Integer amount) {
        if (id == null || id.equals("")) throw new InvalidArgumentException("id is not null ");
        if (productId == null || productId.equals("")) throw new InvalidArgumentException("productId is not null ");
        if(amount ==0){
            throw new BadRequestException("Số lượng không hợp lệ");
        }
        DetailBillEntity detailBill = this.detailBillRepo.findById(id).orElseThrow(()-> new NotFoundException("Detail bill not exist"));

        ProductEntity productEntity = this.productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productEntity.setQuantity(productEntity.getQuantity() - amount);
        this.productRepo.save(productEntity);

        detailBill.setAmount(amountNew);
        this.detailBillRepo.save(detailBill);

        BillResponse.DetailBillResponse billResponse = new BillResponse.DetailBillResponse();
        BeanUtils.copyProperties(detailBill, billResponse);
        return billResponse;
    }
    public BillResponse.DetailBillResponse saveRedis(Integer id, Integer productId, Integer amountNew, Integer amount) {
        if (id == null || id.equals("")) throw new InvalidArgumentException("id is not null ");
        if (productId == null || productId.equals("")) throw new InvalidArgumentException("productId is not null ");
        if(amount ==0){
            throw new BadRequestException("Số lượng không hợp lệ");
        }
        DetailBillEntity detailBill = this.detailBillRepo.findById(id).orElseThrow(()-> new NotFoundException("Detail bill not exist"));

        ProductEntity productEntity = this.productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productEntity.setQuantity(productEntity.getQuantity() - amount);
        this.productRepo.save(productEntity);

        detailBill.setAmount(amountNew);
        this.detailBillRepo.save(detailBill);

        BillResponse.DetailBillResponse billResponse = new BillResponse.DetailBillResponse();
        BeanUtils.copyProperties(detailBill, billResponse);
        return billResponse;
    }
    public BillRequest.DetailBillRequest saveWaitOrderRedis(Integer idBill,String username, BillRequest.DetailBillRequest billRequest) {
        log.info("Add to bill service :: Start");

        SimpleDateFormat format = new SimpleDateFormat("yyMdhms");
        String code = format.format(new Date());

        if (idBill == null || idBill.equals("") || username == null) throw new InvalidArgumentException("id is not null ");
        if (accountRepo.findById(username).isEmpty()) {
            throw new NotFoundException("User not exist");
        }
        BillEntity billEntityRequest = billRepo.findById(idBill).orElseThrow(() -> new NotFoundException("Order không tồn tại"));

        DetailBillRedis detailCartEntity = new DetailBillRedis();
        BillRequest.DetailBillRequest response = new BillRequest.DetailBillRequest();

        ProductEntity productEntity = this.productRepo.findByIdAndStatus(billRequest.getProductId(), status);
        if (ObjectUtils.isEmpty(productEntity)) {
            throw new NotFoundException("Product không tồn tại");

        } else {
            if (productEntity.getQuantity() <= 0) {
                throw new BadRequestException("Số lượng <=0");
            }
            detailCartEntity.setProductId(productEntity.getId());

            DetailBillEntity detailCart = this.detailBillRepo.findByBillIdAndProductId(billEntityRequest.getId(), productEntity.getId());
            if (ObjectUtils.isEmpty(detailCart)) {
                if (detailCart != null) {
                    if (Objects.equals(detailCart.getAmount(), productEntity.getQuantity())) {
                        throw new BadRequestException("Số lượng không hợp lệ");
                    }
                }
                detailCartEntity.setBillId(billEntityRequest.getId());
                if (billRequest.getAmount() > productEntity.getQuantity()) {
                    throw new BadRequestException("Số lượng không hợp lệ");
                } else {
                    detailCartEntity.setAmount(billRequest.getAmount());
                }
            } else {

                if (billRequest.getAmount() + detailCart.getAmount() > productEntity.getQuantity()) {
                    throw new BadRequestException("Số lượng không hợp lệ");
                } else {
                    detailCartEntity.setAmount(billRequest.getAmount() + detailCart.getAmount());
                }
                detailCartEntity.setBillId(billEntityRequest.getId());
                detailCartEntity.setIdDetail(detailCart.getId());
            }
        }
        detailCartEntity.setPrice(productEntity.getPrice());
        detailCartEntity.setPrice(productEntity.getPriceSales());
        detailCartEntity.setBillId(billEntityRequest.getId());
        detailCartEntity.setId(code);

        this.detailBillRedisRepo.create(code,detailCartEntity);

        response.setPrice(detailCartEntity.getPrice());
        response.setPrice(detailCartEntity.getPriceSale());
        response.setId(detailCartEntity.getIdDetail());
        response.setAmount(detailCartEntity.getAmount());

        log.info("Bill- service : End");
        return response;

    }
    public BillResponse update(Integer idOrder, BillEntity.StatusEnum status) {
        if (idOrder == null || idOrder.equals("")) throw new InvalidArgumentException("id is not null ");

        BillEntity billEntity = billRepo.findById(idOrder).get();
        if (ObjectUtils.isEmpty(billEntity)) {
            throw new NotFoundException("Bill not exist");
        }
        billEntity.setStatus(status);
        billEntity = this.billRepo.save(billEntity);

        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(billEntity, billResponse);

        if (billResponse.getStatus().equals(BillEntity.StatusEnum.HUY)) {
            List<DetailBillEntity> orderDetailEntities = detailBillRepo.findByBillId(billEntity.getId());
            for (DetailBillEntity detailBillEntity : orderDetailEntities) {
                ProductEntity productEntity = this.productRepo.findById(detailBillEntity.getProductId()).orElseThrow(() -> new NotFoundException("Product Not Found"));
                productEntity.setQuantity(detailBillEntity.getAmount() + productEntity.getQuantity());
                this.productRepo.save(productEntity);
            }

        }
        return billResponse;
    }

    public BillResponse updateXacNhan(String code, BillEntity.StatusEnum status, BigDecimal customerMoney) {
        if (code == null || code.equals("")) throw new InvalidArgumentException("id is not null ");

        BillEntity billEntity = billRepo.findByCodeBillAndStatus(code, BillEntity.StatusEnum.CHO_XAC_NHAN);
        if (ObjectUtils.isEmpty(billEntity)) {
            throw new NotFoundException("Bill not exist");
        }
        billEntity.setStatus(status);
        billEntity.setCustomerMoney(customerMoney);
        billEntity = this.billRepo.save(billEntity);

        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(billEntity, billResponse);
        return billResponse;
    }


    public DiscountWithBillResponse discount(BigDecimal totalBill) {
        double totalAmountDiscountWithBill = 0;
        double percentDiscountWithBill = 0;
        DiscountWithBillResponse response = new DiscountWithBillResponse();

        List<DiscountWithBillEntity> findAllDiscountWithBill = discountWithBillRepo.getDiscountActive(Sort.by(Sort.Direction.DESC, "discountStart"), 1, new Date());
        if (findAllDiscountWithBill.size() == 0) {
            throw new NotFoundException("Discount không tồn tại");
        }
        DiscountWithBillEntity discount = findAllDiscountWithBill.get(0);
        if (discount.getDiscountType() == 1) {
            totalAmountDiscountWithBill = discount.getDiscountAmount().doubleValue();
        } else {
            if (totalBill.doubleValue() > discount.getMinPrice().doubleValue()) {
                totalAmountDiscountWithBill = totalBill.doubleValue() * (discount.getDiscountAmount().doubleValue() / 100);
                if (totalAmountDiscountWithBill > discount.getMaxPrice().doubleValue()) {
                    totalAmountDiscountWithBill = discount.getMaxPrice().doubleValue();
                }
            }
        }
        BeanUtils.copyProperties(discount, response);
        response.setPriceDiscount(BigDecimal.valueOf(totalAmountDiscountWithBill));

        return response;
    }

    public BillResponse findOrder(Integer idOrder) {
        if (idOrder == null || idOrder.equals("")) throw new InvalidArgumentException("id is not null ");
        Boolean statusCode = true;

        BillEntity billEntity = billRepo.findById(idOrder).orElseThrow(() -> new NotFoundException("Order không tồn tại"));

        BillResponse billResponse = modelMapper.map(billEntity, BillResponse.class);
        billResponse.setStatusName(ConvertStatus.getDeliveredName(billEntity.getStatus(), "vi"));

        List<BillResponse.DetailBillResponse> detailBillResponses = new ArrayList<>();
        List<DetailBillEntity> billDetailEntities = this.detailBillRepo.findByBillId(idOrder);

        billDetailEntities.forEach(orderDetailEntity -> {
            BillResponse.DetailBillResponse detailBillResponse = new BillResponse.DetailBillResponse();
            ProductEntity productEntity = Optional.ofNullable(this.productRepo.findByIdAndStatus(orderDetailEntity.getProductId(), status))
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            BeanUtils.copyProperties(orderDetailEntity, detailBillResponse);
            detailBillResponse.setProductName(productEntity.getFigure());

            CategoriesEntity categoriesEntity = this.categoriesRepo.findById(productEntity.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            List<ImageEntity> findImgProduct = this.imageRepo.findByProductID(productEntity.getId());
            List<RateEntity> rateEntities = this.rateRepo.findByDetailBillID(orderDetailEntity.getId());

            if (rateEntities.size() == 0) {
                detailBillResponse.setRate(false);
            } else {
                detailBillResponse.setRate(true);
            }

            detailBillResponse.setUrl(findImgProduct.get(0).getUrl());
            detailBillResponse.setCategoryName(categoriesEntity.getName());
            detailBillResponse.setHeight(productEntity.getHeight());
            detailBillResponse.setWeight(productEntity.getWeight());
            detailBillResponse.setAmountProduct(productEntity.getQuantity());
            detailBillResponses.add(detailBillResponse);
        });
        billResponse.setDetailBillResponses(detailBillResponses);
        return billResponse;
    }

    public DetailBillRes getDetailBillById(Authentication authentication, int id) {
        Boolean statusCode = true;
        DetailBillEntity billDetailEntities = this.detailBillRepo.findById(id).orElseThrow(() -> new NotFoundException("detailBill khong ton tai!"));
        BillEntity billEntity = this.billRepo.findById(billDetailEntities.getBillId()).orElseThrow(() -> new NotFoundException("bill khong ton tai"));
        if (billEntity.getUsername().equals(authentication.getName())) {
            ProductEntity productEntity = Optional.ofNullable(this.productRepo.findByIdAndStatus(billDetailEntities.getProductId(), status))
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));
            CategoriesEntity categoriesEntity = this.categoriesRepo.findById(productEntity.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            List<ImageEntity> findImgProduct = this.imageRepo.findByProductID(productEntity.getId());
            List<RateEntity> rateEntities = this.rateRepo.findByDetailBillID(id);

            DetailBillRes detailBillRes = new DetailBillRes();
            if (rateEntities.size() == 0) {
                detailBillRes.setIsRate(false);
            } else {
                detailBillRes.setIsRate(true);
            }
            detailBillRes.setId(billDetailEntities.getId());
            detailBillRes.setProductId(productEntity.getId());
            detailBillRes.setNameProduct(productEntity.getFigure());
            detailBillRes.setCategoty(categoriesEntity.getName());
            detailBillRes.setUrlProduct(findImgProduct.get(0).getUrl());
            detailBillRes.setCreatedBy(billDetailEntities.getCreateBy());
            return detailBillRes;
        } else {
            throw new InvalidArgumentException("khong co quyen ");
        }
    }

    public Page<BillResponse> getAllOrder(int page, int pageSize) {
        Page<BillEntity> orderEntityPage = billRepo.findAll(PageRequest.of(page, pageSize));
        if (orderEntityPage.getTotalElements() > 0) {

            return orderEntityPage.map(orderEntity -> modelMapper.map(orderEntity, BillResponse.class)
            );
        }
        throw new NotFoundException("Order not exist");
    }

    public Page<BillResponse> findByUser(int page, int pageSize, String userName) {
        Page<BillEntity> billEntityPage = billRepo.findAllByUsername(userName, PageRequest.of(page, pageSize));
        if (billEntityPage.getTotalElements() > 0) {
            return billEntityPage.map(orderEntity -> modelMapper.map(orderEntity, BillResponse.class));
        }
        throw new NotFoundException("Order not exist");
    }

    public PageData<BillResponse> findByUsernameStatus(SearchFilterPage request, BillEntity.StatusEnum status, String username) {

        int pageNum = request.getPage().getPageNumber();
        int pageSize = request.getPage().getPageSize();
        String fromDate = request.getFromDate();
        String toDate = request.getToDate();
        String sortBy = request.getFilter().getSortBy();
        String sortDirection = request.getFilter().getSortDirection();
        if (accountRepo.findById(username).isEmpty()) {
            throw new NotFoundException("User not exist");
        }
        Specification<BillEntity> receptionistSpecification = (root, cq, cb) -> {
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, username, "username"),
                    QueryUtils.buildGreaterThanFilter(root, cb, "createDate", fromDate, Constant.DATE_FORMAT_3),
                    QueryUtils.buildLessThanFilter(root, cb, "createDate", toDate, Constant.DATE_FORMAT_3),
                    QueryUtils.buildEqFilter(root, cb, "status", status),
            QueryUtils.buildEqFilter(root, cb, "isOnline", true));
        };
        Sort sort;
        if(sortBy == null && sortDirection == null){
            sort = QueryUtils.buildSort("createDate", "desc");
        }
        else {
            sort= QueryUtils.buildSort(sortBy, sortDirection);
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<BillEntity> page = billRepo.findAll(receptionistSpecification, pageable);
        if (page.getTotalElements() > 0) {
            List<BillResponse> billResponses = new ArrayList<>();

            for (BillEntity billEntity : page) {
                BillResponse billResponse = modelMapper.map(billEntity, BillResponse.class);
                billResponse.setStatusName(ConvertStatus.getDeliveredName(billResponse.getStatus(), ""));
                billResponses.add(billResponse);
            }
            return PageData.of(page, billResponses);
        }
        throw new NotFoundException("Order not exist");
    }

    public PageData<BillResponse> findByStatus(SearchFilterPage request, BillEntity.StatusEnum status) {

        int pageNum = request.getPage().getPageNumber();
        int pageSize = request.getPage().getPageSize();
        String fromDate = request.getFromDate();
        String toDate = request.getToDate();
        String codeBill = request.getSearch();
        String phone = request.getSearchV2();
        String sortBy = request.getFilter().getSortBy();
        String sortDirection = request.getFilter().getSortDirection();

        SimpleDateFormat format = new SimpleDateFormat("yyMdhms");
        String code = format.format(new Date());

        Specification<BillEntity> receptionistSpecification = (root, cq, cb) -> {
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, phone, "phoneNumber"),
                    QueryUtils.buildLikeFilter(root, cb, codeBill, "codeBill"),
                    QueryUtils.buildGreaterThanFilter(root, cb, "createDate", fromDate, Constant.DATE_FORMAT_3),
                    QueryUtils.buildLessThanFilter(root, cb, "createDate", toDate, Constant.DATE_FORMAT_3),
                    QueryUtils.buildEqFilter(root, cb, "status", status),
                    QueryUtils.buildEqFilter(root, cb, "isOnline", true));
        };
        Sort sort;
        if(sortBy == null && sortDirection == null){
            sort = QueryUtils.buildSort("createDate", "desc");
        }
        else {
            sort= QueryUtils.buildSort(sortBy, sortDirection);
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<BillEntity> page = billRepo.findAll(receptionistSpecification, pageable);
        if (page.getTotalElements() > 0) {
            List<BillResponse> billResponses = new ArrayList<>();

            for (BillEntity billEntity : page) {
                BillResponse billResponse = modelMapper.map(billEntity, BillResponse.class);
                billResponse.setStatusName(ConvertStatus.getDeliveredName(billEntity.getStatus(), ""));
                billResponses.add(billResponse);
            }
            this.sendPostRequest();
            return PageData.of(page, billResponses);
        }
        throw new NotFoundException("Order not exist");
    }
    public void sendPostRequest() {
        log.info("sendPostRequest :: Start");
        String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "c157a694-916b-11ed-9dc6-f64f768dbc22");
        headers.set("ShopId", "3685860");
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<DeliveryInvoiceEntity> list = this.deliveryInvoiceRepo.findAll();

        for (DeliveryInvoiceEntity deliveryInvoiceEntity : list) {
            BillEntity bill = this.billRepo.findById(deliveryInvoiceEntity.getBillId())
                    .orElseThrow(() -> new NotFoundException("Bill Not Found"));
            if (bill.getStatus().equals(BillEntity.StatusEnum.DA_TAO_DON)) {
                String requestBody = "{\"order_code\": \"" + deliveryInvoiceEntity.getDeliveryId() + "\"}";
                HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONObject dataObject = jsonObject.getJSONObject("data");
                String fieldValue = dataObject.getString("status");
                if(fieldValue.equals("delivering")){
                    this.update(bill.getId(), BillEntity.StatusEnum.DANG_VAN_CHUYEN);
                }
                if (fieldValue.equals("cancel")){
                    this.update(bill.getId(), BillEntity.StatusEnum.HUY_GHN);
                }
                if (fieldValue.equals("delivery_fail")){
                    this.update(bill.getId(), BillEntity.StatusEnum.HUY);
                }
                if(fieldValue.equals("delivered")){
                    this.update(bill.getId(), BillEntity.StatusEnum.DA_GIAO);
                }
                System.out.println("status: delivering" + fieldValue);
            }
        }
        log.info("sendPostRequest :: End");
    }
    public List<BillResponse> findByStatusAndUserId(BillEntity.StatusEnum status, String userName) {
        List<Integer> idOrders;
        if (ObjectUtils.isEmpty(status)) {
            List<BillEntity> billEntities = billRepo.findByUsername(userName);
            idOrders = billEntities.stream().map(BillEntity::getId).collect(Collectors.toList());
        } else {
            List<BillEntity> orderEntities = billRepo.findByUsernameAndStatus(userName, status);
            idOrders = orderEntities.stream().map(BillEntity::getId).collect(Collectors.toList());
        }
        List<BillResponse> response = new ArrayList<>();
        idOrders.forEach(idOrder -> {
            BillResponse billResponse = findOrder(idOrder);
            response.add(billResponse);
        });
        return response;
    }

    public List<BillResponse> findByAddress(String userName) {
        if (userName == null || userName.equals("")) {
            throw new InvalidArgumentException("userName is not null");
        }

        List<Integer> idOrders;
        Set<String> uniqueAddresses = new HashSet<>();
        List<BillEntity> billEntities = billRepo.findByUsername(userName);
        idOrders = billEntities.stream().map(BillEntity::getId).collect(Collectors.toList());
        List<BillResponse> response = new ArrayList<>();

        idOrders.forEach(idOrder -> {
            BillResponse billResponse = findOrder(idOrder);
            if (!uniqueAddresses.add(billResponse.getAddress())) {
                return;
            }

            response.add(billResponse);
        });

        return response;
    }

    public DeliveryInvoiceResponse saveDelivery(DeliveryInvoiceResponse request) {
        DeliveryInvoiceEntity deliveryInvoiceEntity = new DeliveryInvoiceEntity();
        DeliveryInvoiceResponse response = new DeliveryInvoiceResponse();

        BeanUtils.copyProperties(request, deliveryInvoiceEntity);

        deliveryInvoiceEntity = this.deliveryInvoiceRepo.save(deliveryInvoiceEntity);

        BeanUtils.copyProperties(deliveryInvoiceEntity, response);
        return response;
    }

    public DeliveryInvoiceResponse findDelivery(Integer billId) {
        DeliveryInvoiceResponse response = new DeliveryInvoiceResponse();

        DeliveryInvoiceEntity deliveryInvoiceEntity = this.deliveryInvoiceRepo.findByBillId(billId);
        return modelMapper.map(deliveryInvoiceEntity, DeliveryInvoiceResponse.class);
    }
    public void updateRefundBill(Integer idBill, BigDecimal refund){
        if(idBill == null){
            throw new InvalidArgumentException("Bill is null");
        }
        BillEntity orderEntity = this.billRepo.findById(idBill).orElseThrow(() -> new NotFoundException("Hoá đơn không tồn tại"));
        orderEntity.setRefund(refund);
        this.billRepo.save(orderEntity);
    }

    public ByteArrayInputStream exportPdf(Integer orderId) {
        if(orderId == null){
            throw new InvalidArgumentException("Bill is null");
        }
        BillEntity orderEntity = this.billRepo.findById(orderId).orElseThrow(() -> new NotFoundException("Hoá đơn không tồn tại"));
        BigDecimal total = orderEntity.getTotal();
        List<DetailBillEntity> orderDetailEntityList = this.detailBillRepo.findByBillId(orderId);
        AccountEntity user = this.accountRepo.findById(orderEntity.getUsername()).orElse(new AccountEntity());
        List<ExportPdfDTO> exportPdfDTOS = new ArrayList<>();
        for (DetailBillEntity orderDetailEntity : orderDetailEntityList) {
            ProductEntity productEntity = this.productRepo.findById(orderDetailEntity.getProductId()).get();
            ExportPdfDTO exportPdfDTO = new ExportPdfDTO();
            exportPdfDTO.setTotal(BigDecimal.valueOf(orderDetailEntity.getPriceSale().doubleValue() * orderDetailEntity.getAmount()));
            exportPdfDTO.setName(productEntity.getFigure());
            exportPdfDTO.setQuantity(orderDetailEntity.getAmount());
            exportPdfDTO.setWight(productEntity.getWeight());
            exportPdfDTO.setPrice(orderDetailEntity.getPriceSale());
            exportPdfDTOS.add(exportPdfDTO);
        }
        ExportPDFUtils exportPDFUtils = new ExportPDFUtils();

        return exportPDFUtils.exportPdf(exportPdfDTOS, total, user.getUserName(), orderEntity.getCodeBill(), orderEntity.getAddress());
    }

    public BillRequest newWaitingBill(BillRequestv2 billRequestv2){
        SimpleDateFormat format = new SimpleDateFormat("yyMdhms");
        String code = format.format(new Date());
        BillEntity bill = new BillEntity(billRequestv2);
        bill.setStatus(BillEntity.StatusEnum.HOA_DON_CHO_TAI_CUA_HANG);
        bill.setCodeBill("HD" + code + "0");
        bill.setIsOnline(false);
        billRepo.save(bill);
        return null;
    }
    public BillRequest cancelWaitingBill(Integer id){
        BillEntity bill = billRepo.findById(id).orElseThrow(()-> new NotFoundException("Bill not found"));
        bill.setStatus(BillEntity.StatusEnum.HUY);
        billRepo.save(bill);
        List<DetailBillEntity> list = detailBillRepo.findByBillId(bill.getId());
        List<ProductEntity> productEntities = list.stream().map(i->{
            ProductEntity p = productRepo.findById(i.getProductId()).orElseThrow(()->new NotFoundException("Product not found"));
            p.setQuantity(p.getQuantity()+i.getAmount());
            return p;
        }).collect(Collectors.toList());
        productRepo.saveAll(productEntities);
        return null;
    }
    public BillEntity updateWaitingBill(BillRequestv2 billRequestv2){
        BillEntity bill = billRepo.findById(billRequestv2.getId()).orElseThrow(()-> new NotFoundException("Bill not found"));
        bill.setFullName(billRequestv2.getFullName());
        bill.setEmail(billRequestv2.getEmail());
        bill.setUsername(billRequestv2.getUsername());
        bill.setAddress(billRequestv2.getAddress());
        bill.setPhoneNumber(billRequestv2.getPhoneNumber());
        bill = billRepo.save(bill);
        return bill;
    }
    public PageData<BillEntity> getAllWaitingBill(int page,int size,String billCode,String clientName,String from,String to){
        Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        Specification<BillEntity> specification = (root, cq, cb) -> {
            System.out.println("apply status for discount : "+from + " --- "+to);
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, clientName, "fullName"),
                    QueryUtils.buildEqFilter(root, cb, "codeBill",billCode),
                    QueryUtils.buildEqFilter(root, cb, "status", BillEntity.StatusEnum.HOA_DON_CHO_TAI_CUA_HANG),
//                    QueryUtils.buildEqFilter(root, cb, "isOnline", true),
                    QueryUtils.buildGreaterThanFilter(root, cb, "createDate", from, Constant.DATE_FORMAT_2),
                    QueryUtils.buildLessThanFilter(root, cb, "createDate", to, Constant.DATE_FORMAT_2)
            );
//                    QueryUtils.buildEqFilter(root, cb, "status", status));
        };
        Page<BillEntity> pageData = billRepo.findAll(Specification.where(specification).and(
                        (root, cq, cb) -> {
                            return  cb.equal(root.get("isOnline"),false);
                        }
                )
//                BillEntity.StatusEnum.HOA_DON_CHO,true,
//                specification
                ,
                pageable
        );
//        BillEntity bill = new BillEntity();
//        billRepo.save(bill);
        return PageData.of(pageData,pageData.toList());
    }
    public DetailBillResponse addToBillDetail(Integer id, DetailBillDto request){
        BillEntity bill = billRepo.findById(id).orElseThrow(()->new NotFoundException("Bill not found"));
        ProductEntity productEntity = productRepo.findById(request.getProductId()).orElseThrow(()->new NotFoundException("Product not found"));
        if(productEntity.getQuantity() < request.getAmount()){
            return null;
        }
        if(request.getId() == null){
            DetailBillEntity detailBill = new DetailBillEntity();
            detailBill.setAmount(request.getAmount());
            detailBill.setPrice(request.getPrice());
            detailBill.setPriceSale(request.getPriceSale());
            detailBill.setProductId(request.getProductId());
            detailBill.setTypeOfBill(0);
            detailBill.setStatus(0);
            detailBill.setBillId(id);
            detailBill = detailBillRepo.save(detailBill);
            productEntity.setQuantity(productEntity.getQuantity()- request.getAmount());
            productRepo.save(productEntity);
            DetailBillResponse response = new DetailBillResponse(detailBill,productEntity);
            CategoriesEntity categoriesEntity = this.categoriesRepo.findById(productEntity.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            List<ImageEntity> findImgProduct = this.imageRepo.findByProductID(productEntity.getId());
            List<RateEntity> rateEntities = this.rateRepo.findByDetailBillID(detailBill.getId());
            setData(response,categoriesEntity,rateEntities,findImgProduct);

            return  response;
        }
        else{
            DetailBillEntity oldDetailBill = detailBillRepo.findById(request.getId()).orElseThrow(()->new NotFoundException("Detail bill not found"));
            productEntity.setQuantity(productEntity.getQuantity());
            if((productEntity.getQuantity()+oldDetailBill.getAmount()) < request.getAmount()){
                return null;
            }
            DetailBillEntity detailBill = new DetailBillEntity();
            detailBill.setAmount(request.getAmount());
            detailBill.setPrice(request.getPrice());
            detailBill.setPriceSale(request.getPriceSale());
            detailBill.setProductId(request.getProductId());
            detailBill.setTypeOfBill(0);
            detailBill.setStatus(0);
            detailBill.setBillId(id);
            detailBill.setId(request.getId());
            productEntity.setQuantity(productEntity.getQuantity()+oldDetailBill.getAmount()- request.getAmount());
            productRepo.save(productEntity);
            detailBill = detailBillRepo.save(detailBill);
            DetailBillResponse response = new DetailBillResponse(detailBill,productEntity);
            CategoriesEntity categoriesEntity = this.categoriesRepo.findById(productEntity.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            List<ImageEntity> findImgProduct = this.imageRepo.findByProductID(productEntity.getId());
            List<RateEntity> rateEntities = this.rateRepo.findByDetailBillID(detailBill.getId());
            setData(response,categoriesEntity,rateEntities,findImgProduct);

            return  response;

        }
    }
    private DetailBillResponse setData(DetailBillResponse response,CategoriesEntity categoriesEntity,List<RateEntity> rateEntities,List<ImageEntity> image){
        if (rateEntities.size() == 0) {
            response.setRate(false);
        } else {
            response.setRate(true);
        }
        response.setCategoryName(categoriesEntity.getName());
        if(image.size() == 0){
            response.setUrl("No-Image-Placeholder.svg.png");
        }else{
            response.setUrl(image.get(0).getUrl());
        }
        return  response;
    }
    public BillEntity getWaitingBill(String us){
        List<BillEntity> bills = billRepo.findByUsernameAndStatus(us,BillEntity.StatusEnum.HOA_DON_CHO_TAI_CUA_HANG);
        if(bills.size()>0){
            return bills.get(0);
        }
        return  null;
    }
    public void removeFromBillDetail(Integer id){
        DetailBillEntity detailBillEntity =  detailBillRepo.findById(id).orElseThrow(()-> new NotFoundException("Bill detail not found"));
        ProductEntity productEntity = productRepo.findById(detailBillEntity.getProductId()).orElseThrow(()-> new NotFoundException("Product not found"));
        productEntity.setQuantity(productEntity.getQuantity()+detailBillEntity.getAmount());
        productRepo.save(productEntity);
        detailBillRepo.deleteById(detailBillEntity.getId());
    }

    public BillResponseV2 findOrderV2(Integer idOrder) {
        if(idOrder == null || idOrder.equals("")) throw new InvalidArgumentException("id is not null ");
        Boolean statusCode = true;

        BillEntity billEntity = billRepo.findById(idOrder).orElseThrow(() -> new NotFoundException("Order không tồn tại"));
        BillResponseV2 billResponse = new BillResponseV2();
        BeanUtils.copyProperties(billEntity, billResponse);

        List<BillResponseV2.DetailBillResponse> detailBillResponses = new ArrayList<>();
        List<DetailBillEntity> billDetailEntities = this.detailBillRepo.findByBillId(idOrder);

        billDetailEntities.forEach(orderDetailEntity -> {
            BillResponseV2.DetailBillResponse detailBillResponse = new BillResponseV2.DetailBillResponse();
            ProductEntity productEntity = this.productRepo.findById(orderDetailEntity.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            BeanUtils.copyProperties(orderDetailEntity, detailBillResponse);
            detailBillResponse.setProductName(productEntity.getFigure());

            CategoriesEntity categoriesEntity = this.categoriesRepo.findById(productEntity.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            List<ImageEntity> findImgProduct = this.imageRepo.findByProductID(productEntity.getId());
            List<RateEntity> rateEntities = this.rateRepo.findByDetailBillID(orderDetailEntity.getId());

            if (rateEntities.size() == 0) {
                detailBillResponse.setRate(false);
            } else {
                detailBillResponse.setRate(true);
            }
            detailBillResponse.setProductStatus(productEntity.getStatus());
            detailBillResponse.setUrl(findImgProduct.get(0).getUrl());
            detailBillResponse.setCategoryName(categoriesEntity.getName());
            detailBillResponse.setHeight(productEntity.getHeight());
            detailBillResponse.setWeight(productEntity.getWeight());
            detailBillResponses.add(detailBillResponse);
        });
        billResponse.setDetailBillResponses(detailBillResponses);
        return billResponse;
    }
    public Integer saveWaitingBill(BillRequestv2 billResponseV2){
        BillEntity bill = billRepo.findById(billResponseV2.getId()).orElseThrow(()->new NotFoundException("Not found"));
        bill.setTotal(billResponseV2.getTotal());
        bill.setStatus(BillEntity.StatusEnum.DA_GIAO);
        bill.setCustomerMoney(billResponseV2.getCustomerMoney());
        bill.setDiscount(billResponseV2.getDiscount());
        bill.setVoucherCode(billResponseV2.getVoucherCode());
        bill.setDiscount(billResponseV2.getDiscount());
        bill.setPaymentMethod(billResponseV2.getPaymentMethod());
        bill.setTransportFee(billResponseV2.getTransportFee());
        billRepo.save(bill);
        return 1;
    }

}
