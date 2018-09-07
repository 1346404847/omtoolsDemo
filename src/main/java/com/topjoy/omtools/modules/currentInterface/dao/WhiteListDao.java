package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.WhiteList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface WhiteListDao {

    Boolean addWhiteList(WhiteList whiteList);

    Page<WhiteList> getWhiteList(String platId, String  partition, String roleId, int pageNum);

    Boolean upWhiteDataById(String id);
}
