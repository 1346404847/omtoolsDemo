package com.topjoy.omtools.common.service;

import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * @FileName Timestamp.java
 * @Description:时间转换时间戳，时间戳转换时间公共类
 * @author
 * @version V1.0
 * @createtime 2018年8月9日 上午11：47
 */


@Service
public class Timestamp {

    /**
     * 时间转换成时间戳
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time)
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{

            Date date = simpleDateFormat.parse(time);
            long timestamp = date.getTime() / 1000;
            return  timestamp;

        }catch (ParseException e) {
            return  0;
        }

    }


    /**
     * 时间戳转时间(11位时间戳)
     * @param time
     * @return
     */
    public static String timestampToDate( long  time)
    {

        String dateTime;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return  dateTime;

    }


}