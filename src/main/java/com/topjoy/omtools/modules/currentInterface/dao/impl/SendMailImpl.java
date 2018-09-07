package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.tools.internal.xjc.reader.TypeUtil;
import com.topjoy.omtools.common.dao.SpringbootPageable;
import com.topjoy.omtools.common.entity.PageModel;
import com.topjoy.omtools.modules.currentInterface.dao.SendMailDao;
import com.topjoy.omtools.modules.currentInterface.entity.SendMailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import static com.topjoy.omtools.common.util.RandomBatchUtil.mailRandomBatch;


@Component
public class SendMailImpl implements SendMailDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存数据
     * @param data
     * @return
     */
    @Override
    public Boolean saveSendMail(List<Map> data)
    {

        for (Map<String ,Object> item:data) {
            SendMailEntity sendMailEntity = new SendMailEntity();
            sendMailEntity.setBatch(mailRandomBatch());
            sendMailEntity.setTitle((String) item.get("title"));
            sendMailEntity.setContent( (String) item.get("content"));
            sendMailEntity.setStartTime( (String) item.get("startTime"));
            sendMailEntity.setEffectiveTime((String) item.get("effectiveTime"));
            sendMailEntity.setSendType( Integer.parseInt(item.get("sendType").toString()));
            sendMailEntity.setMailType(Integer.parseInt(item.get("mailType").toString()));
            sendMailEntity.setSendPerson( (String) item.get("sendPerson"));
            sendMailEntity.setCreateTime(System.currentTimeMillis()/1000);
            sendMailEntity.setOperator("高光辉");
            sendMailEntity.setPlayerList(item.get("playerLists"));

            System.out.println(item.get("playerLists"));

            try {
                mongoTemplate.save(sendMailEntity);

            }catch (Exception e) {
                System.out.println(e.getMessage());

                return false;
            }
        }
        return true;
    }


    /**
     * 查看详情
     * @param id
     * @return
     */
    @Override
    public SendMailEntity  findSendMailById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        SendMailEntity sendMailEntity = mongoTemplate.findOne(query,SendMailEntity.class);
        return sendMailEntity;
    }

    /**
     * 修改审批状态
     * @param params
     * @return
     */
    @Override
    public Boolean updateSendMail(String params) {
        Query query = new Query();
        Update update = new Update();


        if (!params.isEmpty()) {
            JSONObject jsonObject = JSONObject.parseObject(params);
            query.addCriteria(Criteria.where("id").is(jsonObject.getString("id")));

            if (!jsonObject.getString("approvalStatus").isEmpty()) {
                int approvalStatusInt = Integer.parseInt(jsonObject.getString("approvalStatus"));

                if (approvalStatusInt == 2 || approvalStatusInt==3) {
                    update.set("approvalStatus", approvalStatusInt);
                    update.set("approvalTime", System.currentTimeMillis()/1000);
                    update.set("approvalPerson", "高光辉");
                    update.set("approvalOpinion",jsonObject.getString("approvalOpinion"));
                } else {
                    update.set("approvalStatus", approvalStatusInt);
                }

            }

        }
        try {
            mongoTemplate.updateFirst(query,update,SendMailEntity.class);
        }catch (Exception e) {
             e.getMessage();
            return false;
        }

        return true;

    }


    /**
     * 提供待审批列表
     * @param pageNum
     * @param query
     * @return
     */
    @Override
    public Page<SendMailEntity> pendingList(Integer pageNum ,Query query)
    {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pageModel = new PageModel();
        //开始页
        pageModel.setPagenumber(pageNum);
        pageable.setPage(pageModel);
        // 查询多少条数据
        Long count = mongoTemplate.count(query,SendMailEntity.class);

        List<SendMailEntity> list = mongoTemplate.find(query.with(pageable),SendMailEntity.class);

        Page<SendMailEntity> pageList = new PageImpl<SendMailEntity>(list,pageable,count);

        return pageList;
    }

    /**
     * Execl导入道具
     * @param data
     * @return
     */
    @Override
    public Boolean addSendImportItem(List<Map> data) {
        try {
            for (Map<String, Object> item : data) {
                SendMailEntity sendMailEntity = new SendMailEntity();
                sendMailEntity.setBatch((String) item.get("batch"));
                sendMailEntity.setSendType(Integer.parseInt(item.get("sendType").toString()));
                sendMailEntity.setStartTime((String) item.get("startTime"));
                sendMailEntity.setEffectiveTime((String) item.get("effectiveTime"));
                sendMailEntity.setTitle((String) item.get("title"));
                sendMailEntity.setSendPerson((String) item.get("sendPerson"));
                sendMailEntity.setContent((String) item.get("content"));
                sendMailEntity.setCreateTime(System.currentTimeMillis() / 1000);
                sendMailEntity.setPlayerList(item.get("playerLists"));
                sendMailEntity.setMailType(2);
                //操作人
                sendMailEntity.setOperator("杨浩亮");
                mongoTemplate.insert(sendMailEntity);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return true;
    }

}