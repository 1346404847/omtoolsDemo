package com.topjoy.omtools.modules.currentInterface.service;

import com.topjoy.omtools.modules.currentInterface.dao.IpWhiteDao;
import com.topjoy.omtools.modules.currentInterface.entity.IpWhite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class IpWhiteService {

    @Autowired
    private IpWhiteDao ipWhiteDao;

    private static final Integer NOT_RELIEVED = 1;  //未解除
    /**
     * 添加白名单账号
     * @param ipWhite
     * @return
     */
    public Boolean addWhiteList( IpWhite ipWhite){
        List<String> Ips = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //解析数据 凭凑数据 ,
            //接口调用 验证员工数据 是否正确
            //如果接口调用成功 则插入数据库
            String ipList = ipWhite.getIpList();
            if(ipList!= null){
                Ips = java.util.Arrays.asList(ipList.split(","));
                System.out.println("--------");
                System.out.println(Ips);
                 for (String ip:Ips){
                    IpWhite newIpList = new IpWhite();
                     newIpList.setPlatId(ipWhite.getPlatId());
                     newIpList.setAddStatus(ipWhite.getAddStatus());
                     newIpList.setGameSendStatus(ipWhite.getGameSendStatus());
                     newIpList.setCreateUser(ipWhite.getCreateUser());
                     newIpList.setCreateTime(df.format(new Date()));    //设置当前时间
                     newIpList.setIp(ip);
                     newIpList.setIsRelieve(NOT_RELIEVED);
                     ipWhiteDao.addIpWhite( newIpList );
                 }
            }
        }catch ( Exception e){
            System.out.println("service fail"+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取列表
     * @param platId
     * @param ip
     * @return
     */
    public Page<IpWhite> getIpWhiteList(String platId, String ip,int pageNum){

        return  ipWhiteDao.getIpWhiteList(platId,ip,pageNum);
    }


    /**
     * 解除Ip白名单功能
     * @param ids
     * @return
     */
    public Boolean upIpWhiteDataById(List<String> ids){
        //检查是否在数据库中是否存在
        // 调用接口
        // 修改数据库
        try {
            for (String id :ids){
                ipWhiteDao.upIpWhiteDataById(id);
            }
        }catch (Exception e){
            System.out.println("Service update fail :" + e.getMessage());
            return false;
        }
        return true;
    }

}
