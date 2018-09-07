package com.topjoy.omtools.common.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topjoy.omtools.common.entity.JsonRpcData;
import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.CmdbServiceUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cmdb")
@CrossOrigin(methods = {RequestMethod.GET})
public class CmdbInterfaceController {

    private JSONObject resultData = null;

    /**
     * 获取CMDB所有游戏列表
     * @return
     */
    @GetMapping("/get-version-list")
    public Result getVersionList(){
        // 组装数据
        //  -1:停运 0:研发 1:未开放 2: 运营   不需要已停运的
        List<Integer> paramArr = new ArrayList<Integer>();
        paramArr.add(2);
        paramArr.add(1);
        paramArr.add(0);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("type",1);
        param.put("status",paramArr);
        JsonRpcData jsonRpcData = new JsonRpcData("getCategories",JSON.toJSONString(param) );
        try {
            resultData = CmdbServiceUtil.getData(jsonRpcData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(resultData);
    }

    /**
     * 获取某游戏的下所有平台列表
     * @param gameId
     * @return
     */
    @GetMapping("/get-platform-list")
    public Result getPlatformList(@RequestParam("gameId") int gameId ){
        // 组装数据
        //  -1: 停运 0: 测试 1: 运营
        List<Integer> paramArr = new ArrayList<Integer>();
        paramArr.add(1);
        paramArr.add(0);
        List<String> fieldsArr= new ArrayList<String>();
        fieldsArr.add("id");
        fieldsArr.add("name");
        fieldsArr.add("prefix");
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("main_category_id",gameId);
        param.put("status",paramArr);
        param.put("fields",fieldsArr);
        JsonRpcData jsonRpcData = new JsonRpcData("getPlatforms",JSON.toJSONString(param) );
        try {
            resultData = CmdbServiceUtil.getData(jsonRpcData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(resultData);

    }

    /**
     * 获取平台下区服列表
     * @param platformId
     * @return
     */
    @GetMapping("/get-group-list")
    public Result getGroupList(@RequestParam("platformId") int platformId ){
        // 组装数据
        //  -1:关服 0:测试 1:运营
        List<Integer> paramArr = new ArrayList<Integer>();
        paramArr.add(1);
        paramArr.add(0);
        List<String> fieldsArr= new ArrayList<String>();
        fieldsArr.add("id");
        fieldsArr.add("name");
        fieldsArr.add("prefix");
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("platform_id",platformId);
        param.put("status",paramArr);
        param.put("fields",fieldsArr);
        JsonRpcData jsonRpcData = new JsonRpcData("getGroups",JSON.toJSONString(param) );
        try {
            resultData = CmdbServiceUtil.getData(jsonRpcData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(resultData);

    }




}
