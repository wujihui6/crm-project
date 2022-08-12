package com.wu.crm.commons.utils;

import java.util.UUID;

public class UUIDutil {
    //返回随机uuid的值
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }
}
