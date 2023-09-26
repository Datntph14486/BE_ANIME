package com.qlmh.datn_qlmh.constants;

import java.text.SimpleDateFormat;

public class Constant {
    // format date
    public static String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";

    public static String DATE_FORMAT_2 = "dd-MM-yyyy HH:mm:ss";
    public static String DATE_FORMAT_3= "dd-MM-yyyy";

    public static class VoucherAccountStatus{
        public static Boolean NON_ACTIVE = false;
        public static Boolean USEABLE = true;
        public static Boolean USED = null;


    }
    public static class TypeDiscount{
        public static byte PERCENT=0;
        public static byte MONEY=1;

    }
    public static class AccountStatus{
        public static Integer NON_ACTIVE = null;
        public static Integer ACTIVE = 1;
        public static Integer DISABLE = 0;


    }
    public static class AccountRole{
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
        public static final Long USER_ID = 2l;
        public static final Long ADMIN_ID = 1l;
        public static final Long CLIENT_ID = 3l;
    }



}
