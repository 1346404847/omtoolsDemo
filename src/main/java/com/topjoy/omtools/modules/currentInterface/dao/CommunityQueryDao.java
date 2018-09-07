package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.CommunityEntity;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityQueryEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CommunityQueryDao {

    /**
     * 获取社团查询列表
     *
     * @return result
     */
    CommunityQueryEntity getCommunityQueryList() throws IOException;

    /**
     * 社团数据入库
     *
     * @return result
     */
    int insertCommunityData(CommunityQueryEntity communityQueryEntity);

    /**
     * 获取社团查询列表
     * 测试用
     *
     * @param platformId
     * @param serviceWorkerId
     * @param inputText
     * @return result
     */
    List<CommunityEntity> getCommunitySearchList(String platformId, String serviceWorkerId, String inputText);


    /**
     * 导入社团查询列表
     *
     * @return result
     * @param data
     */
    List<CommunityEntity> importExcelCommunities(Map<String,Object> data) throws FileNotFoundException;

    /**
     * 获取导入社团查询列表
     *
     * @return result
     */
    List<CommunityEntity> getCommunities();
}
