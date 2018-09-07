package com.topjoy.omtools.common.util;


import org.springframework.stereotype.Service;

@Service
public class RandomBatchUtil {

    public static String mailRandomBatch()
    {
        int number = (int)((Math.random()*9+1)*1000);

        String batch = "12" + System.currentTimeMillis()/1000 +number;

        return batch;
    }
}