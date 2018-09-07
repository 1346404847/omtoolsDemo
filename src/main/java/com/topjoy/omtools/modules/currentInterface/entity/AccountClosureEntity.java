package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="account_closure")
public class AccountClosureEntity implements Serializable,Cloneable {
    @Id
    private String id;
    private String platId;
    private String partition;
    private String roleId;      //玩家rid uid openid

    private Integer closureType;    //封禁类型
    private Integer  closureStatus;   //封禁状态
    private String  startTime;
    private String endTime;
    private String remarks;     //备注

    // 查询玩家时使用
    private String vipGrade;    //等级
    private String diamondsNumber;    //钻石数量
    private String goldNumber;   // 金币数量/个
    private String closeSaidStatus;    //禁言状态
    private String accountStatus;    //账号状态

    //上传文件路径
    private String excelFilePath;
    private String batchNumber;     //批次
    private String createTime;     //创建时间


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(String vipGrade) {
        this.vipGrade = vipGrade;
    }

    public String getDiamondsNumber() {
        return diamondsNumber;
    }

    public void setDiamondsNumber(String diamondsNumber) {
        this.diamondsNumber = diamondsNumber;
    }

    public String getGoldNumber() {
        return goldNumber;
    }

    public void setGoldNumber(String goldNumber) {
        this.goldNumber = goldNumber;
    }

    public String getCloseSaidStatus() {
        return closeSaidStatus;
    }

    public void setCloseSaidStatus(String  closeSaidStatus) {
        this.closeSaidStatus = closeSaidStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getClosureType() {
        return closureType;
    }

    public void setClosureType(Integer closureType) {
        this.closureType = closureType;
    }

    public Integer getClosureStatus() {
        return closureStatus;
    }

    public void setClosureStatus(Integer closureStatus) {
        this.closureStatus = closureStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        return (AccountClosureEntity) super.clone();
    }
}
