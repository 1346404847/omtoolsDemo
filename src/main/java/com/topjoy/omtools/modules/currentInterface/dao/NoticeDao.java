package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.NoticeManage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * 公告管理
 */
@Repository
public interface NoticeDao {
    /**
     * 添加公告
     * @param noticeManage
     * @return
     */
    Boolean addNoticeData(NoticeManage noticeManage);

    /**
     * 获取公告列表
     * @param platId
     * @param partition
     * @return
     */
    Page<NoticeManage> getNoticeList(String platId, String  partition, int noticeType , String sendContent,int pageNum);

}
