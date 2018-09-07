package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.AccountClosureEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Repository
public interface AccountClosureDao {

    Boolean addAccountClosure(List<Map> data) throws ParseException;

    Boolean addExcelAccountClosure(List<AccountClosureEntity> data);

    Map<String,Object> getAccountLog(Integer closureStatus, Integer closureType,String startTime,String endTime, String remarks, int pageNum) throws ParseException;
}
