package com.fanfan.feicui.mywidget.bean;

/**
 * Created by Administrator on 2016/5/27.
 */
public class PMBean {
    private String city;//城市
    private String dateTime;//发布时间
    private String curPm;//污染指数
    private String pm25;
    private String pm10;
    private String quality;//污染等级
    private String des;//详细介绍
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCurPm() {
        return curPm;
    }

    public void setCurPm(String curPm) {
        this.curPm = curPm;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



    public PMBean(String city, String dateTime, String curPm, String pm25,
                    String pm10, String quality, String des) {
        this.city = city;
        this.dateTime = dateTime;
        this.curPm = curPm;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.quality = quality;
        this.des = des;
    }
}
