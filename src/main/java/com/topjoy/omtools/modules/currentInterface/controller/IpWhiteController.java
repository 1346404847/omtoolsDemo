package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.entity.IpWhite;
import com.topjoy.omtools.modules.currentInterface.service.IpWhiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ip-white")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET,RequestMethod.POST})
public class IpWhiteController {

    @Autowired
    private IpWhiteService ipWhiteService;
    /**
     * 添加ip白名单
     * @param
     * @return
     */
    @PutMapping("add-ip-white")
    public Result addWhiteList(@RequestBody IpWhite ipWhite)
    {
        try {
            ipWhiteService.addWhiteList(ipWhite);
        }catch (Exception e){
            return Result.insertFailure();
        }
        return Result.success(ResultCode.DATA_INSERT_SUCCESS);

    }

    /**
     * 获取ip白名单列表
     * @param platId
     * @return
     */
    @GetMapping("get-ip-white")
    public Result getIpWhiteList(@RequestParam(name="platId",defaultValue = "",required = false) String platId,
                                  @RequestParam(name="ip",defaultValue = "",required = false) String ip,
                                  @RequestParam(name="pageNum",defaultValue = "1") int pageNum
    ){
        Page<IpWhite> list = ipWhiteService.getIpWhiteList(platId,ip,pageNum);
        return Result.success(list);

    }

    /**
     *  移除Ip白名单
     * @param ids
     * @return
     */
    @PostMapping("update-ip-white")
    public Result updateIpWhiteData(@RequestBody Map<String,List> ids){
        List<String> id = ids.get("id");
        Boolean upRes = ipWhiteService.upIpWhiteDataById(id);
        if(upRes){
            return Result.success();
        }else{
            return  Result.updateFailure();
        }
    }

}
