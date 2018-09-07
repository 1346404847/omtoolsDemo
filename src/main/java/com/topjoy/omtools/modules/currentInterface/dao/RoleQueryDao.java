package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.CardEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleEntity;
import com.topjoy.omtools.modules.currentInterface.entity.RoleQueryEntity;
import org.springframework.data.domain.Page;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface RoleQueryDao {

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    RoleQueryEntity getRoleQueryList();


    /**
     * 角色入库
     * @param roleQueryEntity
     * @return result
     */
    int insertRoleData(RoleQueryEntity roleQueryEntity);


    /**
     * 获取角色查询列表
     * 测试用的
     *
     * @return result
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     */
    List<RoleEntity> getRoleSearchList(String platformId,String serviceWorkerId,String inputText);

    /**
     * 导入角色查询列表
     *
     * @return result
     * @param data
     */
    List<RoleEntity> importExcelRoles(Map<String,Object> data) throws FileNotFoundException;

    /**
     * 获取单个角色信息
     *
     * @param openId
     * @return result
     */
    RoleEntity getOneRoleQuery(String openId);

    /**
     * 更新单个角色信息
     *
     * @param openId
     * @return result
     */
    boolean updateOneRoleQuery(String openId);

    /**
     * 删除单个角色信息
     *
     * @param openId
     * @return result
     */
    boolean deleteOneRoleQuery(String openId);

    /**
     * 所有角色信息
     *
     * @param platformId
     * @param serviceWorkerId
     * @param currentPage
     * @return result
     */
    Page<RoleEntity> getPageRoleQuery(String platformId, String serviceWorkerId, String currentPage);

    /**
     * 获取角色查询列表
     *
     * @return result
     */
    List<RoleEntity> getRolesList();

    /**
     * 获取背包数据信息
     *
     * @param propName
     * @return result
     */
    List<RoleEntity> getPacks(String propName);

    /**
     * 获取卡牌数据信息
     *
     * @return result
     */
    List<CardEntity> getCards();
}
