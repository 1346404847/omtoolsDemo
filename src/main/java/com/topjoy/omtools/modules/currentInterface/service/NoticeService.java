package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.modules.currentInterface.dao.NoticeDao;
import com.topjoy.omtools.modules.currentInterface.entity.NoticeManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    /**
     * 添加公告
     * @param noticeManage
     * @return
     */
    public Boolean addNoticeData( NoticeManage noticeManage){
        try {
            //接口调用
            //如果接口调用成功 则插入数据库
            noticeDao.addNoticeData(noticeManage);

        }catch ( Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取公告列表
     * @param platId
     * @param partition
     * @param noticeType
     * @param SendContent
     * @return
     */
    public Page<NoticeManage> searchNoticeList(String platId, String partition, int noticeType , String SendContent,int pageNum){

        return noticeDao.getNoticeList(platId,partition,noticeType,SendContent,pageNum);
    }


}
