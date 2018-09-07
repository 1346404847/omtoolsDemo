package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.modules.currentInterface.service.SendMailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mail-send")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET})
public class SendMailController {

    @Resource
    private SendMailService sendMailService;

    /**
     * 添加邮件，道具
     * @param data
     * @return
     */

    @RequestMapping(value = "/send-mail",method = RequestMethod.PUT)
    public Result createSendMail(@RequestBody List<Map> data)
    {
        Result result = sendMailService.saveData(data);
        return result;

    }



    /**
     * 查看详情
     * id 为修改那一条数据信息
     * @param id
     * @param
     * @return
     */
    @RequestMapping(value = "/send-info/{id}",method=RequestMethod.GET)
    public Result sendInfo(@PathVariable String id)
    {
        Result result = sendMailService.findSendMailInfo(id);
        return result;
    }

    /**
     * 条件查询列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/send-mail-list/{params}",method = RequestMethod.GET)
    public Result sendMailLists(@RequestBody @PathVariable String params)
    {
        System.out.println(params);
        Result result = sendMailService.mailSearchList(params);
        return result;
    }


    /**
     * 获取待审核列表
     * @return
     */
    @RequestMapping(value = "/wait-approval-list/{params}",method = RequestMethod.GET)
    public Result waitPendingList(@RequestBody @PathVariable String params)
    {

        Result result = sendMailService.pendingSendList(params);
        return  result;
    }


    /**
     * 修改邮件状态approvalStatus 1.待审批、2.已审批、3.已拒绝、4.已撤销
     * @param params
     * @return
     */
    @RequestMapping(value = "/send-mail-update/{params}",method = RequestMethod.GET)
    public Result sendMailUpdateDate(@RequestBody @PathVariable String params)
    {
        Result result = sendMailService.sendMailUpdate(params);
        return result;
    }


    /**
     * 显示已审核列表
     * @return
     */
    @RequestMapping(value = "/audited-list/{params}",method = RequestMethod.GET)
    public Result mailAuditedList(@RequestBody @PathVariable String params)
    {

        Result result = sendMailService.auditedList(params);
        return result;
    }
    /******************************导入发送道具***************************************/

    /**
     * 添加导入道具
     * @param data
     * @return
     * @throws FileNotFoundException
     */
    @PutMapping("import-mail")
    public Result importMail(@RequestBody List<Map> data) throws FileNotFoundException
    {
        try {
            sendMailService.addSendPlayerItem(data);
        }catch (Exception e) {
            return Result.insertFailure();
        }
        return Result.success(true);
    }

}