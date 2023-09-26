package com.qlmh.datn_qlmh.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    public static boolean isValid(Date startDate, Date endDate) {
        Date now = new Date();
        return startDate.before(now) && endDate.after(now);
    }
    public static String modifyDateLayout(String inputDate) throws ParseException {

        Date date = new SimpleDateFormat("yyyymmdd", Locale.US).parse(inputDate);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
