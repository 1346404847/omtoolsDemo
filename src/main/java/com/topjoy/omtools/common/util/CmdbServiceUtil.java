package com.topjoy.omtools.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjoy.omtools.common.entity.JsonRpcData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @Description 请求 cmdb 接口类
 */
@Component
public class CmdbServiceUtil {

    private static InputStream inputStream;
    private static BufferedReader br;

    private static String key ;

    private static String url ;

    private static String secret ;
//    private static String key = "cm" ;
// private static String url = "https://apicmdb.topjoy.com/service.json" ;
//    private static String secret = "25fabfc282e8343ed6d6d3c6d30b2ccf" ;

    public String getSecret() {
        return secret;
    }

    @Value("${cmdb.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    @Value("${cmdb.url}")
    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    @Value("${cmdb.key}")
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @Description: 根据请求参数获取返回数据
     *
     * @param params 请求参数
     * @return JSONObject
     */
    public static JSONObject getData(JsonRpcData params) throws Exception {

        // 创建实例
        HttpClient httpClient = new HttpClient();
        // 设置接口超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        httpClient.getParams().setContentCharset("utf-8");

        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setCredentialCharset("utf-8");
        postMethod.getParams().setHttpElementCharset("utf-8");
        postMethod.getParams().setContentCharset("utf-8");

        //获取当前时间
        String currentTime = getCurrentTime();
        //获取token
        String token = getToken(params.getParams(), currentTime);

        // 设置 http header
        postMethod.addRequestHeader("content-type", "application/json-rpc");
        postMethod.setRequestHeader("type", "application/json-rpc");
        postMethod.setRequestHeader("Date", currentTime);
        postMethod.setRequestHeader("Authorization", "SERVICE " + key + ":" + token);

        // 设置传入参数
        JSONObject paramsBody = new JSONObject();
        paramsBody.put("id", params.getId());
        paramsBody.put("jsonrpc", params.getJsonrpc());
        paramsBody.put("method", params.getMethod());
        paramsBody.put("params", JsonUtil.JSONStrToJSONObject(params.getParams()));

        postMethod.setRequestBody(JsonUtil.JSONObjectToJSONStr(paramsBody));

        // 执行接口请求
        try {
            int statusCode1 = httpClient.executeMethod(postMethod);
            // 判断返回值
            JSONObject response;
            if (statusCode1 != HttpStatus.SC_OK) {
                System.out.println("Method is wrong " + postMethod.getStatusLine() + "----" + statusCode1);
                return null;

            } else {
                // 获取返回值
                inputStream = postMethod.getResponseBodyAsStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = JsonUtil.JSONStrToJSONObject(stringBuffer.toString());
            }
            return response;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            postMethod.releaseConnection();
        }
        return null;
    }

    /**
     * @Description: 获取当前东八区时间
     *
     * @return String
     */
    public static String getCurrentTime() throws Exception {

        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+08:00");

        return simpleDateFormat.format(currentTime);
    }

    /**
     * @Description: 生成 token
     *
     * @param params 请求参数
     * @param currentTime 时间字符
     * @return String
     */
    public static String getToken(String params, String currentTime) throws Exception {
        JSONObject paramsJson = JsonUtil.JSONStrToJSONObject(params);
        String paramsStr = convertDictToStr(paramsJson);
        String encryptionStr = paramsStr + secret + currentTime;
        String tokenStr = EncryptionUtil.getHash(encryptionStr, "MD5");

        return tokenStr ;
    }

    /**
     * @Description: JSONObject 对象转字符串
     *
     * @param params 请求方法参数
     * @return String
     */
    public static String convertDictToStr(JSONObject params) throws Exception {

        String resultData = "";
        if (!params.isEmpty()) {
            String glue = (String) params.getOrDefault("glue", "");

            for (String paramKey :new TreeSet<>( params.keySet())) {
                Object value = params.get(paramKey);
                if (value instanceof JSONArray) {

                    List<String> tmpArrays = JSONObject.parseArray(JsonUtil.JSONArrayToJSONStr((JSONArray) value), String.class);

                    Collections.sort(tmpArrays);

                    List<String> tmpArraysStr = tmpArrays.stream().map(x -> x + "").collect(Collectors.toList());

                    String arraysStr = String.join(glue, tmpArraysStr);

                    resultData += paramKey + arraysStr + glue;
                } else {
                    resultData += paramKey + value + glue;
                }
            }
        }

        return resultData;
    }


    public static String getDataGroup(){


        return "";
    }
    /**
     * @Description: 工具类调用 demo
     *
     * @param args 命令行参数
     * @return void
     */
//    public static void main(String[] args) {
//
//        JsonRpcData jsonRpcData = new JsonRpcData("getEmployees", "{}");
//
//        try {
//
//            JSONObject resultData = CmdbServiceUtil.getData(jsonRpcData);
//
//            System.out.println("resultData >> " + TypeUtil.getType(resultData));    // JSONObject
//            System.out.println("resultData >> " + resultData);
//
//            System.out.println("resultData >> " + TypeUtil.getType(resultData.getJSONArray("result"))); // JSONArray
//            System.out.println("resultData >> " + resultData.getJSONArray("result"));
//
//            for (Object objectItem : resultData.getJSONArray("result")) {
//
//                JSONObject jsonObjectItem = (JSONObject) objectItem;
//
//                System.out.println("resultItem >> " + TypeUtil.getType(jsonObjectItem));    // JSONObject
//                System.out.println("resultItem >> " + jsonObjectItem);
//                System.out.println("resultItem >> " + jsonObjectItem.get("id"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}