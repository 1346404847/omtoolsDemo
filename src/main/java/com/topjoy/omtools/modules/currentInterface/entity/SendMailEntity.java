package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Document(collection="send_prop")
public class SendMailEntity implements Serializable {

    @Id
    private String id;
    //批次
    private String batch;

    //玩家信息包括平台，区服，玩家rids,道具
    private Object playerList;

    //邮件标题
    private String title;

    //邮件内容
    private String content;

    //邮件类型1.仅邮件，2.邮件和道具
    private int mailType;

    //发送类型 1.立即发送，2.定时发送
    private int sendType;

    //操作状态 1.数据正常，2.发送成功，3.发送失败
    private int status = 1;

    //审批状态 1.待审批 2.已审批，3.已拒绝 4.已撤销。
    private int approvalStatus =1;
    //创建时间
    private Long createTime ;

    //发送时间
    private String startTime = "";

    //有效时间
    private String effectiveTime ;
    //落款人
    private String sendPerson;
    //操作人
    private String operator;
    //审批人
    private String approvalPerson ="";

    //审批时间
    private Long approvalTime = 0L;

    //审批意见
    private String approvalOpinion = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    public int getMailType() {
        return mailType;
    }

    public void setMailType(int mailType) {
        this.mailType = mailType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    public String getApprovalPerson() {
        return approvalPerson;
    }

    public void setApprovalPerson(String approvalPerson) {
        this.approvalPerson = approvalPerson;
    }

    public Long getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Long approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String sendTime) {
        this.startTime = sendTime;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }


    public Object getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Object playerList) {
        this.playerList = playerList;
    }
}