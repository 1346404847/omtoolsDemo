package com.topjoy.omtools.modules.currentInterface.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "card")
public class CardEntity {
    @JSONField(name="_id")
    @Id
    private String url;
    private String name;
    private int cardId;
    private int range;
    private String quality;
    private int power;

    public CardEntity() {
        super();
    }

    public CardEntity(String url, String name, int cardId, int range, String quality, int power) {
        super();
        this.url = url;
        this.name = name;
        this.cardId = cardId;
        this.range = range;
        this.quality = quality;
        this.power = power;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
