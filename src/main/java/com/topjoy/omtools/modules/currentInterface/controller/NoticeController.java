package com.topjoy.omtools.modules.currentInterface.controller;


import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.entity.NoticeManage;
import com.topjoy.omtools.modules.currentInterface.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notice-manage")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET})
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 添加公告
     * @param noticeManage
     * @return
     */
    @PutMapping("add-notice")
    public Result addNoticeData(@RequestBody NoticeManage noticeManage)
    {
        Boolean addRes = noticeService.addNoticeData(noticeManage);
        if(addRes){
            return Result.success(ResultCode.DATA_INSERT_SUCCESS);
        }else{
            return Result.failure(ResultCode.DATA_INSERT_FAILURE);
        }

    }

    /**
     * 获取公告列表
     */
    @GetMapping("get-notice")
    public Result  getNoticeList(@RequestParam(name = "platId",defaultValue = "" ) String  platId,
        @RequestParam(name = "partition",defaultValue = "" ) String  partition,
        @RequestParam(name = "noticeType",defaultValue = "0" ) int   noticeType,
        @RequestParam(name = "sendContent",defaultValue = "" ) String  SendContent,
        @RequestParam(name = "pageNum",defaultValue = "1" ) int  pageNum
    ){
        Page<NoticeManage> searchList = noticeService.searchNoticeList(platId,partition,noticeType,SendContent,pageNum);
        return  Result.success(searchList);
    }

}
