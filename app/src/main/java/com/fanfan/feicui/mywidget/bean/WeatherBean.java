package com.fanfan.feicui.mywidget.bean;

/**
 * Created by Administrator on 2016/5/27.
 */
public class WeatherBean {
    public String city;//地区
    public String week;//星期
    public String temperature;//温度
    public String humidity;//湿度
    public String direct;//风向
    public String power;//风级
    public String date;//日期
    public String time;//更新时间
    public String moon;//农历日期
    public String weather;//天气
    public int    id;

    public WeatherBean(String direct, String power, String time, String humidity,
                       String weather, String temperature, String data, String city,
                       String week, String moon, int id) {
        this.city=city;
        this.direct = direct;
        this.power = power;
        this.time = time;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.date = data;
        this.week = week;
        this.moon = moon;
        this.id = id;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}