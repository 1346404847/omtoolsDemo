package com.topjoy.omtools.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Description Json 工具类
 * @date 2018/7/26 下午2:37
 * @version 1.0
 */

public class JsonUtil {

    /**
     * @Description: json str 转 json object
     *
     * @param jsonStr 需要转换的字符串
     * @return JSONObject
     */
    public static JSONObject JSONStrToJSONObject(String jsonStr) throws Exception {
        return JSONObject.parseObject(jsonStr) ;
    }

    /**
     * @Description: json object 转 json str
     *
     * @param jsonObject 需要转换的 json 对象
     * @return String
     */
    public static String JSONObjectToJSONStr(JSONObject jsonObject) throws Exception {
        return JSONObject.toJSONString(jsonObject) ;
    }

    /**
     * @Description: json array str 转 json array object
     *
     * @param jsonStr 需要转换的 json 字符串
     * @return JSONArray
     */
    public static JSONArray JSONStrToJSONArray(String jsonStr) throws Exception {
        return JSONArray.parseArray(jsonStr) ;
    }

    /**
     * @Description: json array object 转 json array str
     *
     * @param jsonArray 需要转换的 json 对象
     * @return String
     */
    public static String JSONArrayToJSONStr(JSONArray jsonArray) throws Exception {
        return JSONArray.toJSONString(jsonArray) ;
    }

    /**
     * @Description: 工具类调用 demo
     *
     * @param args 命令行传参
     * @return void
     */
//    public static void main(String [] args) {
//        String jsonStr = "{\"studentName\":\"lily\",\"studentAge\":12}" ;
//        String jsonArray = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]" ;
//
//        try {
//            // JSONStrToJSONObject 方法测试
//            JSONObject jsonObjectA = JsonUtil.JSONStrToJSONObject(jsonStr) ;
//
//            System.out.println("jsonStr type >> " + TypeUtil.getType(jsonObjectA)) ;
//            System.out.println(jsonObjectA);
//
//            // JSONObjectToJSONStr 方法测试
//            String jsonStrA = JsonUtil.JSONObjectToJSONStr(jsonObjectA) ;
//
//            System.out.println("jsonStrA type >> " + TypeUtil.getType(jsonStrA)) ;
//            System.out.println(jsonStrA);
//
//            // JSONStrToJSONArray 方法测试
//            JSONArray jsonObjectB = JsonUtil.JSONStrToJSONArray(jsonArray) ;
//
//            System.out.println("jsonObjectB type >> " + TypeUtil.getType(jsonObjectB)) ;
//            System.out.println(jsonObjectB);
//
//            // JSONArrayToJSONStr 方法测试
//            String jsonStrB = JsonUtil.JSONArrayToJSONStr(jsonObjectB) ;
//
//            System.out.println("jsonStrB type >> " + TypeUtil.getType(jsonStrB)) ;
//            System.out.println(jsonStrB);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

//    }
}
