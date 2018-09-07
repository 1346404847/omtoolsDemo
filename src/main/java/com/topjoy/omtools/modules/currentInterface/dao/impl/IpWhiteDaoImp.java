package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.topjoy.omtools.common.dao.SpringbootPageable;
import com.topjoy.omtools.common.entity.PageModel;
import com.topjoy.omtools.modules.currentInterface.dao.IpWhiteDao;
import com.topjoy.omtools.modules.currentInterface.entity.IpWhite;
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
 * Ip 白名单实体类
 */
@Component
public class IpWhiteDaoImp implements IpWhiteDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Integer RELIEVED = 2;      // 解除

    /**
     * 添加ip白名单
     * @param ipWhite
     * @return
     */
    @Override
    public Boolean addIpWhite(IpWhite ipWhite) {
        try {
            mongoTemplate.insert(ipWhite);
        }catch (Exception e){
            System.out.println("insert fail ipWhite "+ e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取Ip白名单列表
     * @param platId
     * @param ip
     * @return
     */
    @Override
    public Page<IpWhite> getIpWhiteList(String platId, String ip,int pageNum) {

        Criteria criteria = new Criteria();
        if (!platId.isEmpty()) {
            criteria.and("platId").is(platId);
        }
        if(!ip.isEmpty()){
            criteria.and("ip").is(ip);
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

        Long count = mongoTemplate.count(query,IpWhite.class);
        List<IpWhite> list = mongoTemplate.find(query.with(pageable),IpWhite.class);

        Page<IpWhite> pageList = new PageImpl<>(list,pageable,count);

        return pageList;
    }

    /**
     *  移除数据by id
     * @param id
     * @return
     */
    public Boolean upIpWhiteDataById(String id){
        Query query = new Query();
        Update update = new Update();
        query.addCriteria(Criteria.where("id").is(id));
        update.set("isRelieve",RELIEVED );
        try {
            mongoTemplate.updateFirst(query,update,IpWhite.class);
        }catch (Exception e){
            System.out.println(" ipWhite daoImp up Fail"+ e.getMessage());
            return false;
        }
        return true;
    }


}
