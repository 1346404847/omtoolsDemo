package com.topjoy.omtools.modules.currentInterface.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class RoleEntity {
    @JSONField(name="_id")
    @Id
    private String id;
    private int gameId;           //游戏ID
    private int platformId;       //平台ID
    private int channelId;        //渠道ID
    private int childChannelId;   //子渠道ID
    private int serviceWorkerId;  //区服ID
    private String inputText;     //输入框内容
    private String openId;        //用户openID
    private String UID;           //用户UID
    private String account;       //用户账户
    private int service;          //区服
    private String roleName;      //角色名称
    private int vipRange;         //VIP等级
    private int diamonds;         //钻石数量
    private int goldCoin;         //金币数量
    private Integer accountStatus;
    private Integer anExcuseStatus;
    private String source;        //用户来源
    private int propId;
    private String propPicture;
    private String propName;
    private int propNum;
    private String propDescribe;
    private String lastLoginTime;
    private String registerTime;

    public RoleEntity(String id, int gameId, int platformId, int channelId, int childChannelId, int serviceWorkerId, String inputText, String openId, String UID, String account, int service, String roleName, int vipRange, int diamonds, int goldCoin, Integer accountStatus, Integer anExcuseStatus, String source, int propId, String propPicture, String propName, int propNum, String propDescribe, String lastLoginTime, String registerTime) {
        super();
        this.id = id;
        this.gameId = gameId;
        this.platformId = platformId;
        this.channelId = channelId;
        this.childChannelId = childChannelId;
        this.serviceWorkerId = serviceWorkerId;
        this.inputText = inputText;
        this.openId = openId;
        this.UID = UID;
        this.account = account;
        this.service = service;
        this.roleName = roleName;
        this.vipRange = vipRange;
        this.diamonds = diamonds;
        this.goldCoin = goldCoin;
        this.accountStatus = accountStatus;
        this.anExcuseStatus = anExcuseStatus;
        this.source = source;
        this.propId = propId;
        this.propPicture = propPicture;
        this.propName = propName;
        this.propNum = propNum;
        this.propDescribe = propDescribe;
        this.lastLoginTime = lastLoginTime;
        this.registerTime = registerTime;
    }

    public RoleEntity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getChildChannelId() {
        return childChannelId;
    }

    public void setChildChannelId(int childChannelId) {
        this.childChannelId = childChannelId;
    }

    public int getServiceWorkerId() {
        return serviceWorkerId;
    }

    public void setServiceWorkerId(int serviceWorkerId) {
        this.serviceWorkerId = serviceWorkerId;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getVipRange() {
        return vipRange;
    }

    public void setVipRange(int vipRange) {
        this.vipRange = vipRange;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(int goldCoin) {
        this.goldCoin = goldCoin;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getAnExcuseStatus() {
        return anExcuseStatus;
    }

    public void setAnExcuseStatus(Integer anExcuseStatus) {
        this.anExcuseStatus = anExcuseStatus;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getPropPicture() {
        return propPicture;
    }

    public void setPropPicture(String propPicture) {
        this.propPicture = propPicture;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public int getPropNum() {
        return propNum;
    }

    public void setPropNum(int propNum) {
        this.propNum = propNum;
    }

    public String getPropDescribe() {
        return propDescribe;
    }

    public void setPropDescribe(String propDescribe) {
        this.propDescribe = propDescribe;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
