package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.entity.AccountUpdate;
import com.topjoy.omtools.modules.currentInterface.service.AccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/account-update")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET})
public class AccountUpdateController {

    @Autowired
    private AccountUpdateService accountUpdateService;

    @Value("${fileuploadpath}")
    private  String ExcelFileName;
    /**
     * 添加封禁账号
     * @param
     * @return
     */
    @PutMapping("add-account")
    public Result AccountClosure(@RequestBody List<Map> data)
    {
        accountUpdateService.addAccountUpdate(data);
        return Result.success(ResultCode.DATA_INSERT_SUCCESS);

    }


    /**
     * 处理excel上传文件数据
     * @param accountUpdate
     * @throws FileNotFoundException
     */
    @PutMapping("upload-excel")
    public Result handleUploadExcelData(@RequestBody AccountUpdate accountUpdate) {

        String excelName = ExcelFileName + accountUpdate.getExcelFilePath();
        try {
            accountUpdateService.insertUploadExcelData(excelName,accountUpdate);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Result.insertFailure();
        }
        return Result.success(ResultCode.DATA_INSERT_SUCCESS);
    }

}
