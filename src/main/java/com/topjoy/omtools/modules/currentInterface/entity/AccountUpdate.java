package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="account_update")
public class AccountUpdate implements Serializable,Cloneable {
    @Id
    private String id;
    private String platId;
    private String  partition;
    private String roleId;      //玩家rid uid openid

    private Integer vipNumber;    //等级
    private Integer diamonds;    //钻石数量
    private Integer gold;   // 金币数量/个

    private String closeSaidStatus;    //禁言状态
    private String accountStatus;    //账号状态

    private Integer operationVip;        //要扣除vip
    private Integer operationDiamonds;    //要扣除钻石
    private Integer operationGold;       //要扣除金币
    private String remarks;     //备注
    private String createTime;     //备注

    private String excelFilePath;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(Integer vipNumber) {
        this.vipNumber = vipNumber;
    }

    public Integer getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(Integer diamonds) {
        this.diamonds = diamonds;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public String getCloseSaidStatus() {
        return closeSaidStatus;
    }

    public void setCloseSaidStatus(String closeSaidStatus) {
        this.closeSaidStatus = closeSaidStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getOperationVip() {
        return operationVip;
    }

    public void setOperationVip(Integer operationVip) {
        this.operationVip = operationVip;
    }

    public Integer getOperationDiamonds() {
        return operationDiamonds;
    }

    public void setOperationDiamonds(Integer operationDiamonds) {
        this.operationDiamonds = operationDiamonds;
    }

    public Integer getOperationGold() {
        return operationGold;
    }

    public void setOperationGold(Integer operationGold) {
        this.operationGold = operationGold;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return (AccountUpdate) super.clone();
    }
}
