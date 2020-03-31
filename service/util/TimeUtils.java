package com.geek.guiyu.service.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 获取时间
 * @date 2020/3/31 10:42 AM
 */
public class TimeUtils {
    private static String SS = "ss";
    private static String DD = "dd";
    //设置日期格式，24h制
    //24h制,df1为create_time.update_time. df2为生日
    private static SimpleDateFormat df1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat df2 = new SimpleDateFormat ("yyyy-MM-dd");

    public static Date getTime(String type) throws ParseException {
        Date date = null;
        String nowTime = "";
        if (SS.equals(type)){
            //如果为 yyyy-MM-dd HH:mm:ss 格式
            nowTime = df1.format(new Date());
            date = df1.parse(nowTime);
        } else {
            nowTime = df2.format(new Date());
            date = df2.parse(nowTime);
        }
       return date;
    }
}
