package com.wu.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateutil {
    //转化日期的格式方法
    public static String dateformatyear(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(date);
        return nowStr;
    }

    //转化日期的格式方法
    public static String dateformattime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = sdf.format(date);
        return nowStr;
    }
}
