package com.topjoy.omtools.modules.currentInterface.dao;

import com.topjoy.omtools.modules.currentInterface.entity.AccountUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccountUpdateDao {

    /**
     * 表单添加数据
      * @param data
     * @return
     */
    Boolean addAccountUpdate(List<Map> data);

    /**
     * excel 导入
     * @param data
     * @return
     */
    Boolean addExcelAccountUpdate(List<AccountUpdate> data);
}
