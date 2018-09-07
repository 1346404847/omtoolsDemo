package com.topjoy.omtools.modules.currentInterface.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToDateUtil {
    /**
     * 时间转换方法
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static String  dateToString( String dateStr) throws ParseException {
        dateStr = dateStr.replace("Z", " UTC");
        System.out.println(dateStr);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = format.parse(dateStr);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//需要转化成的时间格式
        String str1 = format1.format(d);
        return str1;
    }

    /**
     * 时间转换  Thu May 18 2017 00:00:00 GMT+0800 (中国标准时间)  转换成 2018-1-1 12：00：00
     * @param dateStr
     * @return
     */
    public static String parseTimeToString(String dateStr){
        String newTimeStr = "";
        dateStr = dateStr.replace("GMT", "").replaceAll("\\(.*\\)", "");
        //将字符串转化为date类型，格式2016-10-12
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date dateTrans = null;
        try {
            dateTrans = format.parse(dateStr);
            newTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTrans);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTimeStr;
    }
}
