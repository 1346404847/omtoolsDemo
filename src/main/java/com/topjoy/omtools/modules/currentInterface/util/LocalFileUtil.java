package com.topjoy.omtools.modules.currentInterface.util;

import java.io.File;
import java.util.Scanner;

public class LocalFileUtil {

    /**
     * 读取文件内容为字符串
     *
     * @param file
     * @return
     */
    public static String jsonRead(File file){
        Scanner scanner      = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
}
