package com.topjoy.omtools.common.controller;

import com.topjoy.omtools.common.util.DownloadExecl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import static com.topjoy.omtools.common.service.ExeclFileFormat.playerFile;
import static com.topjoy.omtools.common.service.ExeclFileFormat.sendMailFile;

@RestController
@RequestMapping(value = "/download-execl")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET})
public class DownloadExeclFileController {
    @Resource
    private DownloadExecl downloadExecl;

    /*******************************下载Execl文件功能*****************************/

    @GetMapping(value = "download-file/{params}")
    public void downloadExeclFile(HttpServletRequest request , HttpServletResponse response, @RequestBody @PathVariable String params)  throws Exception {
        String fileName = params+".xlsx";
        downloadExecl.downloadExeclFile(request , response ,fileName);

    }


    /*******************************导出Execl文件功能*****************************/
    @GetMapping(value = "common-download/{params}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@RequestBody @PathVariable String params) throws IOException {
        ByteArrayOutputStream bos = null;
        String filename = "test.xlsx";
        try {

            Workbook workbook;
            if (params.equals("player")) {
                workbook = playerFile();
            }else {
                workbook = sendMailFile();
            }

            bos = new ByteArrayOutputStream();
            workbook.write(bos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("charset", "utf-8");
            //设置下载文件名
            filename = URLEncoder.encode(filename, "UTF-8");
            headers.add("Content-Disposition", "attachment;filename=\"" + filename + "\"");

            org.springframework.core.io.Resource resource = new InputStreamResource(new ByteArrayInputStream(bos.toByteArray()));

            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/x-msdownload")).body(resource);

        } catch (IOException e) {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }


}