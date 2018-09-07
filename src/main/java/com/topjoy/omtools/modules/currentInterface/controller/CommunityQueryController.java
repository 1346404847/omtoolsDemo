package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityQueryEntity;
import com.topjoy.omtools.modules.currentInterface.service.CommunityQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/community")
@CrossOrigin(methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class CommunityQueryController {

    @Autowired
    private CommunityQueryService communityQueryService;

    /**
     * 社团数据入库
     *
     * @return result
     */
    @PostMapping("/insert-community-data")
    public Result insertCommunityData(@RequestBody CommunityQueryEntity communityQueryEntity)
    {
        return communityQueryService.insertCommunityData(communityQueryEntity);
    }

    /**
     * 获取社团查询列表
     *
     * @return result
     */
    @GetMapping("/get-community-query")
    public Result getCommunityQueryList() throws IOException {
        return communityQueryService.getCommunityQueryList();
    }

    /**
     * 获取社团查询列表
     * 测试用
     *
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     * @return result
     */
    @GetMapping("/get-community-search")
    public Result getCommunitySearchList(@RequestParam(value = "platformId",required = false,defaultValue = "") String platformId,
                                         @RequestParam(value = "serviceWorkerId",required = false,defaultValue = "") String serviceWorkerId,
                                         @RequestParam(value = "inputText",required = false,defaultValue = "") String inputText)
    {
        return communityQueryService.getCommunitySearchList(platformId,serviceWorkerId,inputText);
    }

    /**
     * 导入社团查询列表
     *
     * @return result
     */
    @PostMapping("/import-community")
    public Result importExcelCommunities(@RequestBody Map<String,Object> data) throws FileNotFoundException {
        return communityQueryService.importExcelCommunities(data);
    }

    /**
     * 获取导入社团查询列表
     *
     * @return result
     */
    @GetMapping("/get-communities")
    public Result getCommunities() {
        return communityQueryService.getCommunities();
    }


}