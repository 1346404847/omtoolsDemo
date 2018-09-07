package com.topjoy.omtools.modules.currentInterface.controller;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.modules.currentInterface.entity.RoleQueryEntity;
import com.topjoy.omtools.modules.currentInterface.service.RoleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/role")
@CrossOrigin(methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class RoleQueryController {

    @Autowired
    private RoleQueryService roleQueryService;

    /**
     * 角色入库
     * @param roleQueryEntity
     * @return result
     */
    @PostMapping("/insert-role-data")
    public Result insertRoleData(@RequestBody RoleQueryEntity roleQueryEntity)
    {
        return roleQueryService.insertRoleData(roleQueryEntity);
    }

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    @GetMapping("/get-role-query")
    public Result getRoleQueryList()
    {
        return roleQueryService.getRoleQueryList();
    }

    /**
     * 获取角色查询列表
     * 测试用
     *
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     * @return result
     */
    @GetMapping("/get-role-search")
    public Result getRoleSearchList(@RequestParam(value = "platformId",required = false,defaultValue = "") String platformId,
                                    @RequestParam(value = "serviceWorkerId",required = false,defaultValue = "") String serviceWorkerId,
                                    @RequestParam(value = "inputText",required = false,defaultValue = "") String inputText)
    {
        return roleQueryService.getRoleSearchList(platformId,serviceWorkerId,inputText);
    }

    /**
     * 导入角色查询列表
     *
     * @return result
     */
    @PostMapping("/import-role")
    public Result importExcelRoles(@RequestBody Map<String,Object> data) throws FileNotFoundException {
        return roleQueryService.importExcelRoles(data);
    }

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    @GetMapping("/get-roles-list")
    public Result getRolesList(){
        return roleQueryService.getRolesList();
    }

    /**
     * 获取单个角色信息
     *
     * @param openId
     * @return result
     */
    @GetMapping("/get-one-role")
    public Result getOneRoleQuery(@RequestParam(value = "openId",required = false,defaultValue = "") String openId){
        return roleQueryService.getOneRoleQuery(openId);
    }

    /**
     * 更新单个角色信息
     *
     * @param openId
     * @return result
     */
    @PutMapping("/update-one-role")
    public Result updateOneRoleQuery(@RequestParam(value = "openId",required = false,defaultValue = "") String openId){
        return roleQueryService.updateOneRoleQuery(openId);
    }

    /**
     * 删除单个角色信息
     *
     * @param openId
     * @return result
     */
    @DeleteMapping("/delete-one-role")
    public Result deleteOneRoleQuery(@RequestParam(value = "openId",required = false,defaultValue = "") String openId){
        return roleQueryService.deleteOneRoleQuery(openId);
    }

    /**
     * 所有角色信息
     *
     * @param platformId
     * @param serviceWorkerId
     * @param currentPage
     * @return result
     */
    @GetMapping("/get-page-role")
    public Result getPageRoleQuery(@RequestParam(value = "platformId",required = false,defaultValue = "0") String platformId,
                                   @RequestParam(value = "serviceWorkerId",required = false,defaultValue = "0") String serviceWorkerId,
                                   @RequestParam(value = "currentPage",required = false,defaultValue = "10") String currentPage){
        return roleQueryService.getPageRoleQuery(platformId,serviceWorkerId,currentPage);
    }

    /**
     * 获取背包数据信息
     *
     * @param propName
     * @return result
     */
    @GetMapping("/get-packs")
    public Result getPacks(@RequestParam(value = "propName",required = false,defaultValue = "") String propName){
        return roleQueryService.getPacks(propName);
    }

    /**
     * 获取卡牌数据信息
     *
     * @return result
     */
    @GetMapping("/get-cards")
    public Result getCards(){
        return roleQueryService.getCards();
    }

}