package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.topjoy.omtools.common.dao.SpringbootPageable;
import com.topjoy.omtools.common.entity.PageModel;
import com.topjoy.omtools.modules.currentInterface.dao.WhiteListDao;
import com.topjoy.omtools.modules.currentInterface.entity.WhiteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户白名单实体类
 */
@Component
public class WhiteListDaoImp implements WhiteListDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final Integer RELIEVED = 2;      // 解除状态
    public static final Integer NOT_RELIEVED = 1;      // 未解除

    /**
     * 添加
     * @param whiteList
     * @return
     */
    @Override
    public Boolean addWhiteList(WhiteList whiteList) {
        try {
            mongoTemplate.insert(whiteList);
        }catch (Exception e){
            System.out.println("daoImp insert fail"+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取白名单列表
     * @param platId
     * @param partition
     * @param roleId
     * @return
     */
    @Override
    public Page<WhiteList> getWhiteList(String platId, String  partition, String roleId, int pageNum) {

        Criteria criteria = new Criteria();
        if (!platId.isEmpty()) {
            criteria.and("platId").is(platId);
        }
        if(!partition.isEmpty()){
            criteria.and("partition").is(partition);
        }
        if(!roleId.isEmpty()){
            criteria.and("roleId").is(roleId);
        }

        Query query = new Query(criteria);

        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pageModel = new PageModel();
        //开始页
        pageModel.setPagenumber(pageNum);
        pageable.setPage(pageModel);

        List<Sort.Order>orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "isRelieve"));
        query.with(new Sort(orders));

        Long count = mongoTemplate.count(query,WhiteList.class);
        List<WhiteList> list = mongoTemplate.find(query.with(pageable),WhiteList.class);

        Page<WhiteList> pageList = new PageImpl<>(list,pageable,count);
        return pageList;

    }

    /**
     *  移除数据by id
     * @param id
     * @return
     */
    public Boolean upWhiteDataById(String id){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria(Criteria.where("id").is(id));
        update.set("isRelieve",RELIEVED );      // 设置为解除状态
        try {
            mongoTemplate.updateFirst(query,update,WhiteList.class);
        }catch (Exception e){
            System.out.println("daoImp update Fail"+ e.getMessage());
            return false;
        }

        return true;
    }


}
