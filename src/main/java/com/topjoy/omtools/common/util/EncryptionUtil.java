package com.topjoy.omtools.common.util;

import java.security.MessageDigest;

/**
 * 加密类
 */
public class EncryptionUtil {
    /**
     * @Description: 使用格式化方式，将加密后的数据转换成16进制
     * @param source 需要加密的字符串
     * @param hashType 加密类型 （MD5 和 SHA）
     * @return String
     */
    public static String getHash(String source, String hashType) throws Exception {
        StringBuilder strBuilder = new StringBuilder();

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(hashType);
            md5.update(source.getBytes("utf-8"));

            for (byte b : md5.digest()) {
                strBuilder.append(String.format("%02x", b)); // 10进制转16进制，x 表示以十六进制形式输出，02 表示不足两位前面补0输出
            }

            return strBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 工具类调用 demo
     *
     * @param args 命令行参数
     * @return void
     */
//    public static void main(String [] args ) {
//
//        try {
//            String md5Str = EncryptionUtil.getHash("aaaa", "MD5") ;
//            System.out.println("md5Str >> " + md5Str);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
