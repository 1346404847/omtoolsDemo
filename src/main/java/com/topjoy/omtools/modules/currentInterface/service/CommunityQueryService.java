package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.common.entity.Result;
import com.topjoy.omtools.common.util.ResultCode;
import com.topjoy.omtools.modules.currentInterface.dao.CommunityQueryDao;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityEntity;
import com.topjoy.omtools.modules.currentInterface.entity.CommunityQueryEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CommunityQueryService {

    @Resource
    private CommunityQueryDao communityQueryDao;

    /**
     * 获取社团查询列表
     *
     * @return result
     */
    public Result getCommunityQueryList() throws IOException {
        CommunityQueryEntity data = communityQueryDao.getCommunityQueryList();
        return Result.success(data);
    }

    /**
     * 社团数据入库
     *
     * @return result
     */
    public Result insertCommunityData(CommunityQueryEntity communityQueryEntity)
    {
        int insert = communityQueryDao.insertCommunityData(communityQueryEntity);
        return Result.success(insert);
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
    public Result getCommunitySearchList(String platformId, String serviceWorkerId, String inputText) {
        List<CommunityEntity> data = communityQueryDao.getCommunitySearchList(platformId,serviceWorkerId,inputText);
        return Result.success(data);
    }

    /**
     * 导入社团查询列表
     *
     * @return result
     * @param data
     */
    public Result importExcelCommunities(Map<String,Object> data) throws FileNotFoundException {
        List<CommunityEntity> Communities = communityQueryDao.importExcelCommunities(data);
        if(Communities != null){
            return Result.success(Communities);
        }else{
            return Result.fail(ResultCode.DATA_INSERT_FAILURE,null);
        }
    }

    /**
     * 获取导入社团查询列表
     *
     * @return result
     */
    public Result getCommunities() {
        List<CommunityEntity> communitiesList = communityQueryDao.getCommunities();
        if(communitiesList != null){
            return Result.success(communitiesList);
        }else{
            return Result.fail(ResultCode.RESTFUL_DATA_NONE,null);
        }
    }
}
