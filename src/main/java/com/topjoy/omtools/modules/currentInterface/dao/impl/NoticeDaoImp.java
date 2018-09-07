package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.topjoy.omtools.common.dao.SpringbootPageable;
import com.topjoy.omtools.common.entity.PageModel;
import com.topjoy.omtools.modules.currentInterface.dao.NoticeDao;
import com.topjoy.omtools.modules.currentInterface.entity.NoticeManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.topjoy.omtools.modules.currentInterface.util.ToDateUtil.dateToString;

/**
 * 公告列表页面
 */
@Component
public class NoticeDaoImp implements NoticeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Integer GAME_OUT = 1;      // 游戏外公告
    private static final Integer GAME_WITH = 2;      // 游戏内公告
    private static final Integer HORSE_RACE_LAMP = 3;      // 跑马灯 horse

    private static final Integer SEND_TIMING = 1;      // 定时发送
    private static final Integer SEND_NOW = 2;      // 立刻发送


    @Override
    public Boolean addNoticeData(NoticeManage noticeManage) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 游戏外公告不能有区服
            if(noticeManage.getNoticeType() == GAME_OUT){
                noticeManage.setPartition("");
            }
            // 定时发送必须选择时间。
            if(noticeManage.getSendType() == SEND_TIMING){
                noticeManage.setStartTime(dateToString((String) noticeManage.getSendTime().get(0)));
                noticeManage.setEndTime(dateToString((String) noticeManage.getSendTime().get(1)));
            }
            noticeManage.setCreateTime(df.format(new Date()));
            mongoTemplate.insert(noticeManage);

        }catch (Exception e){
            System.out.println("daoImp insert fail"+e.getMessage());
            return false;
        }
        return true;
    }



    /**
     * 获取公告列表
     * @param platId
     * @param partition
     * @param noticeType
     * @param sendContent
     * @return
     */
//    @Override
    public Page<NoticeManage> getNoticeList(String platId, String  partition, int noticeType ,String sendContent,int pageNum) {
        Criteria criteria = new Criteria();
        if (!platId.isEmpty()) {
            criteria.and("platId").is(platId);
        }
        if(!partition.isEmpty()){
            criteria.and("partition").is(partition);
        }
        if(noticeType !=0 ){
            criteria.and("noticeType").is(noticeType);
        }
        if(!sendContent.isEmpty() ){
            criteria.and("sendContent").regex(sendContent);
        }
        Query query = new Query(criteria);

        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pageModel = new PageModel();
        //开始页
        pageModel.setPagenumber(pageNum);
        pageable.setPage(pageModel);

        List<Sort.Order>orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        query.with(new Sort(orders));

        Long count = mongoTemplate.count(query,NoticeManage.class);
        List<NoticeManage> list = mongoTemplate.find(query.with(pageable),NoticeManage.class);

        Page<NoticeManage> pageList = new PageImpl<NoticeManage>(list,pageable,count);

        return pageList;

    }




}
