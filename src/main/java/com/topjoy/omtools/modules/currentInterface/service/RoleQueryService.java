package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.dao.RoleQueryDao;
import com.topjoy.omtools.modules.currentInterface.entity.CardEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleQueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class RoleQueryService {

    @Resource
    private RoleQueryDao roleQueryDao;

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    public Result getRoleQueryList()
    {
        RoleQueryEntity data = roleQueryDao.getRoleQueryList();
        return Result.success(data);
    }

    /**
     * 获取角色查询列表
     * 测试用的
     *
     * @return result
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     */
    public Result getRoleSearchList(String platformId,String serviceWorkerId,String inputText)
    {
        List<RoleEntity> rolesList = roleQueryDao.getRoleSearchList(platformId,serviceWorkerId,inputText);
        return Result.success(rolesList);
    }

    /**
     * 角色入库
     * @param roleQueryEntity
     *
     * @return result
     */
    public Result insertRoleData(RoleQueryEntity roleQueryEntity)
    {
        int insert = roleQueryDao.insertRoleData(roleQueryEntity);
        return Result.success(insert);
    }

    /**
     * 导入角色查询列表
     *
     * @return result
     * @param data
     */
    public Result importExcelRoles(Map<String,Object> data) throws FileNotFoundException {
        List<RoleEntity> roles = roleQueryDao.importExcelRoles(data);
        if(roles != null){
            return Result.success(roles);
        }else{
            return Result.fail(ResultCode.DATA_INSERT_FAILURE,null);
        }
    }

    /**
     * 获取单个角色信息
     *
     * @param openId
     * @return result
     */
    public Result getOneRoleQuery(String openId) {
        RoleEntity role = roleQueryDao.getOneRoleQuery(openId);
        return Result.success(role);
    }

    /**
     * 更新单个角色信息
     *
     * @param openId
     * @return result
     */
    public Result updateOneRoleQuery(String openId) {
        boolean up = roleQueryDao.updateOneRoleQuery(openId);
        if(up){
            return Result.updateSuccess();
        }else{
            return Result.updateFailure();
        }
    }

    /**
     * 删除单个角色信息
     *
     * @param openId
     * @return result
     */
    public Result deleteOneRoleQuery(String openId) {
        return Result.success(roleQueryDao.deleteOneRoleQuery(openId));
    }

    /**
     * 所有角色信息
     *
     * @param platformId
     * @param serviceWorkerId
     * @param currentPage
     * @return result
     */
    public Result getPageRoleQuery(String platformId, String serviceWorkerId, String currentPage) {
        Page<RoleEntity> rolesList = roleQueryDao.getPageRoleQuery(platformId,serviceWorkerId,currentPage);
        if(rolesList != null){
            return Result.success(rolesList);
        }else{
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,null);
        }
    }

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    public Result getRolesList() {
        List<RoleEntity> rolesList = roleQueryDao.getRolesList();
        if(rolesList != null){
            return Result.success(rolesList);
        }else {
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,null);
        }

    }

    /**
     * 获取背包数据信息
     *
     * @param propName
     * @return result
     */
    public Result getPacks(String propName) {
        List<RoleEntity> packsList = roleQueryDao.getPacks(propName);
        if(packsList != null && !packsList.isEmpty()){
            return Result.success(packsList);
        }else{
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,null);
        }
    }

    /**
     * 获取卡牌数据信息
     *
     * @return result
     */
    public Result getCards() {
        List<CardEntity> cards = roleQueryDao.getCards();
        if(cards != null){
            return Result.success(cards);
        }else{
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,null);
        }
    }
}
