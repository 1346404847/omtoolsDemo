package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.topjoy.omtools.modules.currentInterface.dao.AccountClosureDao;
import com.topjoy.omtools.modules.currentInterface.entity.AccountClosureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.topjoy.omtools.modules.currentInterface.util.ToDateUtil.dateToString;

@Component
public class AccountClosureDaoImp implements AccountClosureDao {

    @Value("${pageNumber}")
    private Integer pageNumber;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 封禁操作
     * @param data
     * @return
     */
    public Boolean addAccountClosure(List<Map> data) throws ParseException {

        String  batchNumber = "account"+System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createTime = df.format(new Date());

        for (Map<String ,Object> item:data){
            AccountClosureEntity accountClosureEntity = new AccountClosureEntity();
            accountClosureEntity.setPlatId((String) item.get("platId"));
            accountClosureEntity.setPartition((String) item.get("partition"));
            accountClosureEntity.setRoleId((String) item.get("roleId"));
            accountClosureEntity.setClosureType((Integer ) item.get("closureType"));
            accountClosureEntity.setClosureStatus((Integer ) item.get("closureStatus"));
            accountClosureEntity.setStartTime(dateToString((String) item.get("startTime")));
            accountClosureEntity.setEndTime(dateToString((String) item.get("endTime")));
            accountClosureEntity.setRemarks((String) item.get("remarks"));
            accountClosureEntity.setBatchNumber(batchNumber);
            accountClosureEntity.setCreateTime(createTime);
            if(item.get("excelFilePath")!=""){
                accountClosureEntity.setExcelFilePath((String) item.get("excelFilePath"));
            }
            mongoTemplate.insert(accountClosureEntity);
        }

        return true;
    }

    /**
     * excel批量插入 封禁操作
     * @param data
     * @return
     */
    public Boolean addExcelAccountClosure(List<AccountClosureEntity> data) {

        mongoTemplate.insertAll(data);
        return true;
    }

    /**
     * 获取批次列表
     * @param closureStatus
     * @param closureType
     * @param remarks
     * @param pageNum
     * @return
     */
    public Map<String,Object> getAccountLog(Integer closureStatus,Integer closureType, String startTime ,String endTime,String remarks,int pageNum) throws ParseException {

        Criteria criteria = new Criteria();
        if (closureStatus != 0) {
            criteria.and("closureStatus").is(closureStatus);
        }
        if(closureType != 0 ){
            criteria.and("closureType").is(closureType);
        }
        if(!startTime.isEmpty() ){
            criteria.andOperator(Criteria.where("createTime").lt(endTime),Criteria.where("createTime").gt(startTime));
        }

        if(!remarks.isEmpty() ){
            criteria.and("remarks").regex(remarks);
        }

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("batchNumber").
                        last("createTime").as("createTime").
                        last("closureType").as("closureType").
                        last("closureStatus").as("closureStatus").
                        last("batchNumber").as("batchNumber").
                        last("startTime").as("startTime").
                        last("endTime").as("endTime"),

                Aggregation.sort(Sort.Direction.DESC,"createTime"),
                Aggregation.skip((pageNum-1)*pageNumber),    //从第几条开始
                Aggregation.limit(pageNumber)    // 取多少条
        );
        //获取数据
        AggregationResults<Map> result = mongoTemplate.aggregate(agg, "account_closure", Map.class);
        List<Map> mappedResults = result.getMappedResults();

        //获取总数量
        Aggregation aggcount = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("batchNumber")
        );
        AggregationResults countRes = mongoTemplate.aggregate(aggcount, "account_closure", Map.class);
        List<Map> countList = countRes.getMappedResults();

        Map<String,Object> allData = new HashMap<>();
        allData.put("AllCount",countList.size());
        allData.put("Data",mappedResults);

        return allData;
    }

}
