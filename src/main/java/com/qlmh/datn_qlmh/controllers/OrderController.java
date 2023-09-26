package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.Items.FilterItems;
import com.qlmh.datn_qlmh.dtos.Items.PageItem;
import com.qlmh.datn_qlmh.dtos.Items.SearchFilterPage;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.BillRequest;
import com.qlmh.datn_qlmh.dtos.request.BillRequestv2;
import com.qlmh.datn_qlmh.dtos.request.DetailBillDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherReq;
import com.qlmh.datn_qlmh.dtos.response.BillResponse;
import com.qlmh.datn_qlmh.dtos.response.BillResponseV2;
import com.qlmh.datn_qlmh.dtos.response.DeliveryInvoiceResponse;
import com.qlmh.datn_qlmh.dtos.response.DetailBillRes;
import com.qlmh.datn_qlmh.entities.BillEntity;
import com.qlmh.datn_qlmh.entities.DeliveryStatus;
import com.qlmh.datn_qlmh.services.impl.BillServiceImpl;
import com.qlmh.datn_qlmh.services.impl.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/bill")
public class OrderController {
    private final DeliveryService deliveryService;
    private final BillServiceImpl billService;
    @Autowired
    public OrderController(DeliveryService deliveryService, BillServiceImpl billService) {
        this.deliveryService = deliveryService;
        this.billService = billService;
    }

    @GetMapping("/orders")
    public String getOrderDetails() {
        return deliveryService.getOrderDetails();
    }
    @GetMapping("/delivery/{trackingNumber}")
    public DeliveryStatus getDeliveryStatus(@PathVariable String trackingNumber) {
        return deliveryService.getDeliveryStatus(trackingNumber);
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveOrder(@RequestBody @Valid BillRequest billRequest) {

        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.saveOrder(billRequest)));
    }
    @PostMapping("/save-delivery")
    public ResponseEntity<?> saveDelivery(@RequestBody @Valid DeliveryInvoiceResponse deliveryInvoiceResponse) {

        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.saveDelivery(deliveryInvoiceResponse)));
    }
    @PostMapping("/update-refund")
    public void updateBillRefund(@RequestParam("id") Integer idBill,@RequestParam("refund") BigDecimal refund) {
        billService.updateRefundBill(idBill, refund);
    }
    @Operation(summary = "export order", description = "export order")
    @GetMapping("export/{id}")
    public ResponseEntity<?> exportOrder(@PathVariable("id") Integer id
    ) {
        ByteArrayInputStream byteArrayInputStream = billService.exportPdf(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Despoisition","inline; filename:order.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/find-delivery/{id}")
    public ResponseEntity<?> findDelivery(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,billService.findDelivery(id)));
    }
    @GetMapping("/find-address/{username}")
    public ResponseEntity<?> findAddress(@PathVariable("username") String username) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,billService.findByAddress(username)));
    }
    @PostMapping("/applyVoucher")
    public Double voucher(@RequestParam("voucherCode") String voucherCode,@RequestParam("username") String username,@RequestParam("total") BigDecimal total){
        return billService.voucher(voucherCode,username, total);
    }
    @PostMapping("/update/voucher")
    public  void  voucher(@RequestParam("code") String code, @RequestParam("username") String username, @RequestParam("status") String status){
        this.billService.update(code, username, status);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "page_size") int pageSize) {
        Page<BillResponse> productDtoPage = billService.getAllOrder(page, pageSize);
        return ResponseEntity.ok(productDtoPage);
    }

    @Operation(summary = "Lấy order theo id", description = "Lấy order theo id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,billService.findOrder(id)));
    }

    @Operation(summary = "Lấy order theo  useId", description = "Lấy order theo  useId")
    @GetMapping("/user")
    public ResponseEntity<?> getOrderByUserId(@RequestParam(value = "page") int page,
                                              @RequestParam(value = "page_size") int pageSize,
                                              @RequestParam("user_name") String userName) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.findByUser(page, pageSize, userName)));
    }
    @Operation(summary = "Lấy order theo  useId", description = "Lấy order theo  status")
    @GetMapping("/status")
    public ResponseEntity<?> getOrderByStatus(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                              @RequestParam(value = "status", required = false) BillEntity.StatusEnum status,
                                              @RequestParam (value = "sortBy", required = false) String sortBy,
                                              @RequestParam(value = "sortDirection", required = false) String sortDirection,
                                              @RequestParam(value = "from", required = false) String fromDate,
                                              @RequestParam(value = "to", required = false) String toDate,
                                              @RequestParam(value = "search", required = false) String codeBill,
                                              @RequestParam(value = "phone", required = false) String phone


    ) {
        SearchFilterPage request = new SearchFilterPage();
        request.setFilter(new FilterItems(sortBy, sortDirection));
        request.setPage(new PageItem(pageNum, pageSize));
        request.setSearch(codeBill);
        request.setSearchV2(phone);
        request.setToDate(toDate);
        request.setFromDate(fromDate);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.findByStatus(request, status)));
    }
    @GetMapping("/username")
    public ResponseEntity<?> getOrderByUsername(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                                @RequestParam(value = "status", required = false) BillEntity.StatusEnum status,
                                                @RequestParam (value = "sortBy", required = false) String sortBy,
                                                @RequestParam(value = "sortDirection", required = false) String sortDirection,
                                                @RequestParam(value = "from", required = false) String fromDate,
                                                @RequestParam(value = "to", required = false) String toDate,
                                                @RequestParam(value = "search", required = true) String username
    ) {
        SearchFilterPage request = new SearchFilterPage();
        request.setFilter(new FilterItems(sortBy, sortDirection));
        request.setPage(new PageItem(pageNum, pageSize));
        request.setToDate(toDate);
        request.setFromDate(fromDate);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.findByUsernameStatus(request, status, username)));
    }
    @Operation(summary = "update order", description = "update order")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Integer id,
                                         @RequestBody BillEntity.StatusEnum status) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.update(id, status)));
    }
    @Operation(summary = "update order confirm", description = "update order confirm")
    @PostMapping("/xac_nhan/{code}")
    public ResponseEntity<?> updateOrderConfirm(@PathVariable("code") String code,
                                         @RequestBody BillEntity.StatusEnum status,@RequestParam(value = "customer", defaultValue = "0") BigDecimal customer) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.updateXacNhan(code, status, customer)));
    }
    @PostMapping("/delete/{id}/{productId}")
    public void delete(@PathVariable("id") Integer id,@PathVariable("productId") Integer productId,
                       @RequestParam(value = "amount", defaultValue = "0") int amount) {
       this.billService.delete(id, amount, productId);
    }
    @Operation(summary = "update bill", description = "update bill")
    @PostMapping("/update-bill")
    public ResponseEntity<?> updateBill(@RequestBody BillRequest  request) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.updateBill(request)));
    }
    @Operation(summary = "update amount", description = "update amount")
    @PostMapping("/update-amount")
    public ResponseEntity<?> updateAmount( @RequestParam(value = "id") Integer id,
                                           @RequestParam(value = "productId") Integer productId,
                                           @RequestParam(value = "amount") Integer amount,
                                           @RequestParam(value = "amountNew") Integer amountNew) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.updateAmount(id, productId,amountNew, amount)));
    }
    @Operation(summary = "update wait order", description = "update wait order")
    @PostMapping("/{id}/orderwait")
    public ResponseEntity<?> updateOrderWait(@PathVariable("id") Integer id,
                                             @RequestParam(value = "username") String username,
                                             @RequestBody BillRequest.DetailBillRequest  request) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.updateWaitOrder(id,username,request)));
    }

    @GetMapping("detailbill/{id}")
    public ResponseEntity<?> getDetailBillById(@PathVariable("id") Integer id, Authentication authentication){
   return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,billService.getDetailBillById(authentication,id)));
    }
    @PostMapping("/discount-bill")
    public ResponseEntity<?> discountBill(@RequestParam("total") BigDecimal total){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.discount(total)));
    }

    @PostMapping("/new-waiting-bill")
    public ResponseEntity<?> newWaitingBill(@RequestBody() BillRequestv2 bill){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.newWaitingBill(bill)));
    }
    @PostMapping("/update-waiting-bill")
    public ResponseEntity<?> updateWaitingBill(@RequestBody() BillRequestv2 bill){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.updateWaitingBill(bill)));
    }
    @DeleteMapping("/cancel-waiting-bill")
    public ResponseEntity<?> cancelWaitingBill(@RequestParam("id") Integer id){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.cancelWaitingBill(id)));
    }
    @GetMapping("/get-waiting-bill")
    public ResponseEntity<?> getAllWaitingBill(@RequestParam(value = "page", defaultValue = "0") int pageNum,
                                               @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "from", defaultValue = "") String from,
                                               @RequestParam(value = "to", defaultValue = "") String to,
                                               @RequestParam(value = "maHd", defaultValue = "") String billCode,
                                               @RequestParam (value = "clientName", defaultValue = "") String clientName
    ){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.getAllWaitingBill(pageNum,pageSize,billCode,clientName,from,to)));
    }
    @PostMapping("/add-to-cart/{id}")
    public ResponseEntity<?> addProductToOrder(@Valid @RequestBody DetailBillDto request, @PathVariable("id") Integer id){
        System.out.println(request);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.addToBillDetail(id,request)));
    }
    @GetMapping("/remove-to-cart/{id}")
    public ResponseEntity<?> removeProductToOrder(@PathVariable("id") Integer id){
        billService.removeFromBillDetail(id);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS));
    }
    @GetMapping("/get-waiting-bill-by-username")
    public ResponseEntity<?> getWaitingBill(@RequestParam(value = "username") String us
    ){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.getWaitingBill(us)));
    }
    @GetMapping("/order-waiting/{id}")
    public ResponseEntity<?> getOrderWaitingById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,billService.findOrderV2(id)));
    }
    @PostMapping("/save-waiting-bill")
    public ResponseEntity<?> saveOrderWaiting(@RequestBody BillRequestv2 request){
        System.out.println(request);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS, billService.saveWaitingBill(request)));
    }

}
