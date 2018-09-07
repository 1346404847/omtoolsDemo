package com.topjoy.omtools.modules.currentInterface.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.core.mapping.Document;

@EntityScan
@Document(collection = "vega_community_query")
public class CommunityQueryEntity {
    private int gameId;           //游戏ID

    private int platformId;       //平台ID

    private int channelId;        //渠道ID

    private int childChannelId;   //子渠道ID

    private int serviceWorkerId;  //区服ID

    private String inputText;     //输入框内容

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

}
