package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.entity.AccountClosureEntity;
import com.topjoy.omtools.modules.currentInterface.service.AccountClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/account-closure")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET})
public class AccountClosureController {

    @Autowired
    private AccountClosureService accountClosureService;

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
        accountClosureService.addAccountClosure(data);
        return Result.success(ResultCode.DATA_INSERT_SUCCESS);

    }

    /**
     * 获取员工信息
     * @param platId
     * @param partition
     * @param roleId
     * @return
     */
    @GetMapping("get-user-info")
    public Result getuserInfo(@RequestParam("platId") String platId, @RequestParam("partition") String  partition,@RequestParam("roleId") String roleId)
    {
        Map<String,Object> getList = accountClosureService.searchUserInfo(platId,partition,roleId);
        return Result.success(getList);
    }

    /**
     * 处理excel上传文件数据
     * @param accountClosureEntity
     * @throws FileNotFoundException
     */
    @PutMapping("upload-excel")
    public Result handleUploadExcelData(@RequestBody AccountClosureEntity accountClosureEntity) throws FileNotFoundException {

        System.out.println(accountClosureEntity.getStartTime());
        if(accountClosureEntity.getPlatId().isEmpty()){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        String excelName = ExcelFileName + accountClosureEntity.getExcelFilePath();
        accountClosureService.insertUploadExcelData(excelName,accountClosureEntity);

        return Result.success(ResultCode.DATA_INSERT_SUCCESS);
    }

    /**
     * 获取批次列表（批次分组）
     * @param closureStatus
     * @param closureType
     * @param remarks
     * @param pageNum
     * @return
     */
    @GetMapping("get-account-log")
    public Result getAccountLog(@RequestParam(name = "closureStatus",defaultValue = "0") int closureStatus,
                                @RequestParam(name = "closureType",defaultValue = "0") int  closureType,
                                @RequestParam(name = "startTime",required = false) String startTime,
                                @RequestParam(name = "endTime",required = false) String endTime,
                                @RequestParam(name = "remarks",defaultValue = "") String remarks,
                                @RequestParam(name = "pageNum" ,defaultValue = "1") int pageNum
                                ) throws ParseException {
        Map<String,Object> list=  accountClosureService.getAccountLog(closureStatus,closureType,startTime,endTime,remarks, pageNum);
        return Result.success(list);
    }


}
