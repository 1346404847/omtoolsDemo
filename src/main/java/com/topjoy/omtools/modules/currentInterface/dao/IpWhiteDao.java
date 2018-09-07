package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.IpWhite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Ip白名单 dao
 */
@Repository
public interface IpWhiteDao {

    /**
     * 添加Ip白名单
     * @param ipWhite
     * @return
     */
    Boolean addIpWhite(IpWhite ipWhite);

    /**
     * 获取Ip白名单列表
     * @param platId
     * @param ip
     * @return
     */
    Page<IpWhite> getIpWhiteList(String platId, String ip,int pageNum);

    /**
     * 修改IP白名单 by id
     * @param id
     * @return
     */
    Boolean upIpWhiteDataById(String id);
}
