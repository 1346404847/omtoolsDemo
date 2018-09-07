package com.topjoy.omtools.common.util;


import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;




@Service
public class DownloadExecl {


    /**
     * 下载文件
     * @param request
     * @param response
     * @param propTemplate
     * @throws Exception
     */
    public void downloadExeclFile(HttpServletRequest request, HttpServletResponse response,String propTemplate)  throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        request.setCharacterEncoding("UTF-8");

        String fileName = propTemplate;

        try {

            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"downloadExecl/"+fileName);
            response.setContentType("application/x-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            response.setHeader("Content-Length",String.valueOf(file.length()));
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len=in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


}