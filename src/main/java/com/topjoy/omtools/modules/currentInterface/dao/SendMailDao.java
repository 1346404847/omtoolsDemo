package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.SendMailEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;


public interface SendMailDao {

    //添加数据
    Boolean saveSendMail(List<Map> data);

    //更新数据
    Boolean updateSendMail(String params);

    //查看详情
    SendMailEntity findSendMailById(String id);

    //提供待审批列表
    Page<SendMailEntity> pendingList(Integer pageNum ,Query query);

    Boolean addSendImportItem(List<Map> data);

    
}