package com.topjoy.omtools.modules.currentInterface.dao.impl;

import com.topjoy.omtools.modules.currentInterface.dao.AccountUpdateDao;
import com.topjoy.omtools.modules.currentInterface.entity.AccountUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AccountUpdateDaoImp implements AccountUpdateDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加账号修改数据
     * @param data
     * @return
     */
    public Boolean addAccountUpdate(List<Map> data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Map<String ,Object> item:data){
            AccountUpdate accountUpdate = new AccountUpdate();
            accountUpdate.setPlatId((String) item.get("platId"));
            accountUpdate.setPartition((String) item.get("partition"));
            accountUpdate.setRoleId((String) item.get("roleId"));
            accountUpdate.setOperationVip((Integer) item.get("operationVip"));
            accountUpdate.setOperationGold((Integer) item.get("operationGold"));
            accountUpdate.setOperationDiamonds((Integer) item.get("operationDiamonds"));
            accountUpdate.setRemarks((String) item.get("remarks"));
            accountUpdate.setCreateTime(df.format(new Date()));
            if(item.get("excelFilePath")!=""){
                accountUpdate.setExcelFilePath((String) item.get("excelFilePath"));
            }
            mongoTemplate.insert(accountUpdate);
        }

        return true;
    }


    /**
     * excel批量插入 封禁操作
     * @param data
     * @return
     */
    public Boolean addExcelAccountUpdate(List<AccountUpdate> data) {
        mongoTemplate.insertAll(data);
        return true;
    }



}
