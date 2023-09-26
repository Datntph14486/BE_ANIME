package com.qlmh.datn_qlmh.utilities;

import com.qlmh.datn_qlmh.entities.BillEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertStatus {
    public static String getProductStatusName(int status,String lang) {
        switch (status) {
            case 1:
                return MessageUtils.get(lang, "msg.status.active");
            case 0:
                return MessageUtils.get(lang, "msg.status.inactive");
            default:
                return MessageUtils.get(lang, "msg.status.draft");

        }
    }
    public static Date convertDate(Date inputDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(inputDate);

        Date date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formattedDate);
        return date;
    }
    public static String discount(Byte discount, String lang){
        switch (discount) {
            case 1:
                return MessageUtils.get(lang, "msg.discount-type.amount");
            case 2:
                return MessageUtils.get(lang, "msg.discount-type.percent");
            default:
                return MessageUtils.get(lang, "msg.status.error");

        }
    }
    public static String getDeliveredName(BillEntity.StatusEnum status, String lang) {
        switch (status) {
            case HOA_DON_CHO:
                return MessageUtils.get(lang, "msg.status.hoa_don_cho");
            case CHO_XAC_NHAN:
                return MessageUtils.get(lang, "msg.status.cho_xac_nhan");
            case XAC_NHAN:
                return MessageUtils.get(lang, "msg.status.xac_nhan");
            case DA_TAO_DON:
                return MessageUtils.get(lang, "msg.status.da_tao_don");
            case DANG_VAN_CHUYEN:
                return MessageUtils.get(lang, "msg.status.dang_van_chuyen");
            case DA_VE_KHO:
                return MessageUtils.get(lang, "msg.status.da_ve_kho");
            case DANG_GIAO:
                return MessageUtils.get(lang, "msg.status.dang_giao");
            case DA_GIAO:
                return MessageUtils.get(lang, "msg.status.da_giao");
            case HUY:
                return MessageUtils.get(lang, "msg.status.huy");
            case TRA_HANG:
                return MessageUtils.get(lang, "msg.status.tra_hang");
            case HUY_GHN:
                return MessageUtils.get(lang, "msg.status.huy_ghn");
            default:
                return MessageUtils.get(lang, "msg.status.error");

        }
    }
    public static String getOrderName(int status,String lang) {
        switch (status) {
            case 1:
                return MessageUtils.get(lang, "msg.status.ordered");
            case 2:
                return MessageUtils.get(lang, "msg.status.wait-confirmed");
            case 3:
                return MessageUtils.get(lang, "msg.status.confirmed");
            case 4:
                return MessageUtils.get(lang, "msg.status.preparing");
            case 5:
                return MessageUtils.get(lang, "msg.status.delivered-to-the-carrier");
            case 6:
                return MessageUtils.get(lang, "msg.status.transported");
            case 7:
                return MessageUtils.get(lang, "msg.status.delivered");
            default:
                return MessageUtils.get(lang, "msg.status.cancel");

        }
    }
    public static String orderType(int status, String lang){
        switch (status) {
            case 1:
                return MessageUtils.get(lang, "msg.status.order-pre");
            case 2:
                return MessageUtils.get(lang, "msg.status.order-available");
            default:
                return MessageUtils.get(lang, "No body");

        }
    }
}
