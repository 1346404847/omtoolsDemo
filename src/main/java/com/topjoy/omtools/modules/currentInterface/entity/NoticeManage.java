package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 公告管理
 */
@Document(collection="notice_manage")
public class NoticeManage {

    @Id
    private String id;
    private String platId;          // 平台
    private String partition;       //区服
    private int noticeType;        //1 游戏外公告 2游戏内公告 3跑马灯公告
    private int sendType;          //类别  1定时发送  2 立即发送
    private String sendContent;     //内容
    private List sendTime;        //sendTime不参与任何 计算和逻辑只是为了给start 和end 分配数据
    private String startTime;        //发送开始时间
    private String endTime;        //发送结束时间
    private String sendUser;        //落款人
    private String createTime;

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

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
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

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public List getSendTime() {
        return sendTime;
    }

    public void setSendTime(List sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
