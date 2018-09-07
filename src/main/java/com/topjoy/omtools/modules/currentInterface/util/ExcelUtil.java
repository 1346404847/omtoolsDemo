package com.topjoy.omtools.modules.currentInterface.util;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/excel")
@CrossOrigin(methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE})
public class ExcelUtil {

    @Value("${excelfileuploadpath}")
    private String excelFileUploadPath;

    /**
     * 备注：方法所传参数file要对应Vue插件的name属性值，否则上传错误，code=-1
     * @param file
     * @return Result
     */
    @PostMapping(value = "/upload-file")
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,"文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 解决中文问题，liunx下中文路径，图片显示问题
        String realFileName   = UUID.randomUUID() + suffixName;

        File fileDir = new File(excelFileUploadPath + realFileName);
        // 检测是否存在目录
        if (!fileDir.getParentFile().exists()) {
            fileDir.getParentFile().mkdirs();
        }
        try {
            file.transferTo(fileDir);
            return Result.success(realFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @ 描述：是否是2003的excel，返回true是2003
     * @param filePath
     * @return Result
     */
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @ 描述：是否是2007的excel，返回true是2007
     * @param filePath
     * @return Result
     */
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateUpdateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }
}