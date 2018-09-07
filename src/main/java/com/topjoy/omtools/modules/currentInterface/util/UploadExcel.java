package com.topjoy.omtools.modules.currentInterface.util;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具
 */
@RestController
@RequestMapping(value = "/upload-excel")
public class UploadExcel {

    @Value("${fileuploadpath}")
    private String fileUploadPath;

    @CrossOrigin(methods = { RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.GET })
    @PostMapping(value = "/upload-file")
    public Result getUploadFilePath(@RequestParam(value = "excelfile",required = false) MultipartFile file)
    {
        Map<String,Object> map = new HashMap<>();
        /**----上传文件start-----**/
        if (file.isEmpty()) {
            return Result.failure(ResultCode.DATA_UPLOAD_EMPTY,null);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Long format =   new Date().getTime();
        //设置新的文件名字
        String newFileName = "omtool_"+format+suffixName;
        //设置文件上传后的路径
        String filePath = fileUploadPath;
        File dest = new File(filePath + newFileName);
        if (!dest.getParentFile().exists()) {
            if(!dest.getParentFile().mkdirs()){
                return Result.failure(ResultCode.DATA_UPLOAD_PATH,null);
            }
        }
        try {
            file.transferTo(dest);  // 转存文件
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  Result.success(newFileName);
    }


}
