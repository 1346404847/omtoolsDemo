package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ip_white")
public class IpWhite {
    @Id
    private String id;
    private String platId;
    private String ip;      //玩家rid uid openid

    private String addStatus;       //添加状态
    private String gameSendStatus;    //发送状态
    private String createUser;               // 操作人
    private String createTime;    //操作时间

    private int isRelieve;      // 1 未解除 2解除
    private String ipList;    //id列表


    public int getIsRelieve() {
        return isRelieve;
    }

    public void setIsRelieve(int isRelieve) {
        this.isRelieve = isRelieve;
    }

    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(String addStatus) {
        this.addStatus = addStatus;
    }

    public String getGameSendStatus() {
        return gameSendStatus;
    }

    public void setGameSendStatus(String gameSendStatus) {
        this.gameSendStatus = gameSendStatus;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
