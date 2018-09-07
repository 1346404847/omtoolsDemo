package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;

import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.entity.WhiteList;
import com.topjoy.omtools.modules.currentInterface.service.WhiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/white-list")
@CrossOrigin(methods = {RequestMethod.PUT,RequestMethod.GET,RequestMethod.POST})
public class WhiteListController {

    @Autowired
    private WhiteListService whiteListService;

    /**
     * 添加白名单
     * @param
     * @return
     */
    @PutMapping("add-white")
    public Result addWhiteList(@RequestBody WhiteList whiteList)
    {
        try {
            whiteListService.addWhiteList(whiteList);
        }catch (Exception e){
            return Result.insertFailure();
        }
        return Result.success(ResultCode.DATA_INSERT_SUCCESS);

    }

    /**
     * 获取白名单列表
     * @param platId
     * @param partition
     * @param roleId
     * @return
     */
    @GetMapping("get-white")
    public Result getAddWhiteList(@RequestParam(name="platId",defaultValue = "",required = false) String platId,
                                  @RequestParam(name="partition",defaultValue = "",required = false) String partition,
                                  @RequestParam(name="roleId",defaultValue = "",required = false) String roleId,
                                  @RequestParam(name = "pageNum",defaultValue = "1" ) int  pageNum

    ){
        Page<WhiteList> list = whiteListService.getWhiteList(platId,partition,roleId,pageNum);
        return Result.success(list);

    }

    /**
     * 解除白名单功能
     * @param ids
     * @return
     */
    @PostMapping("update-white")
    public Result updateWhiteData(@RequestBody Map<String,List> ids){
        List<String> id = ids.get("id");
        Boolean upRes = whiteListService.upWhiteDataById(id);
        if(upRes){
            return Result.updateSuccess();
        }else{
            return  Result.updateFailure();
        }
    }

}
