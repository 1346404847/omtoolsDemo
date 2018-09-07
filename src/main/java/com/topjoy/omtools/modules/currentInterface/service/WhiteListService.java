package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.modules.currentInterface.dao.WhiteListDao;
import com.topjoy.omtools.modules.currentInterface.entity.WhiteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.topjoy.omtools.modules.currentInterface.dao.impl.WhiteListDaoImp.NOT_RELIEVED;

@Service
public class WhiteListService {

    @Autowired
    private WhiteListDao whiteListDao;

    /**
     * 添加白名单账号
     * @param whiteList
     * @return
     */
    public Boolean addWhiteList( WhiteList whiteList){
        List<String> RoIds = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //解析数据 凭凑数据 ,
            //接口调用 验证员工数据 是否正确
            //如果接口调用成功 则插入数据库

            String idList = whiteList.getRoleIdList();
            if(idList!= null){
                 RoIds = java.util.Arrays.asList(idList.split(","));
                 for (String roleId:RoIds){
                    WhiteList newWhiteList = new WhiteList();
                     newWhiteList.setRoleId(roleId);
                     newWhiteList.setPlatId(whiteList.getPlatId());
                     newWhiteList.setPartition(whiteList.getPartition());
                     newWhiteList.setAddStatus(whiteList.getAddStatus());
                     newWhiteList.setGameSendStatus(whiteList.getGameSendStatus());
                     newWhiteList.setCreateUser(whiteList.getCreateUser());
                     newWhiteList.setCreateTime(df.format(new Date()));    //设置当前时间
                     newWhiteList.setRoleId(roleId);
                     newWhiteList.setIsRelieve(NOT_RELIEVED);
                     whiteListDao.addWhiteList( newWhiteList );
                 }
            }
        }catch ( Exception e){
            System.out.println("==》fail《==");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取列表
     * @param platId
     * @param partition
     * @param roleId
     * @return
     */
    public Page<WhiteList> getWhiteList(String platId, String partition, String roleId, int pageNum){

        return  whiteListDao.getWhiteList(platId,partition,roleId,pageNum);
    }

    /**
     * 移除白名单功能
     * @param ids
     * @return
     */
    public Boolean upWhiteDataById(List<String> ids){
        //检查是否在数据库中是否存在

        // 调用接口
        // 修改数据库
        try {
            for (String id :ids){
                whiteListDao.upWhiteDataById(id);
            }
        }catch (Exception e){
            System.out.println("update fail :" + e.getMessage());
            return false;
        }
        return true;

    }

}
