package com.fanfan.feicui.mywidget.util;

import android.net.Uri;

import com.fanfan.feicui.mywidget.bean.LifeBean;
import com.fanfan.feicui.mywidget.bean.PMBean;
import com.fanfan.feicui.mywidget.bean.WeatherBean;
import com.fanfan.feicui.mywidget.bean.WeatherInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * http://op.juhe.cn/onebox/weather/query
 * 929add6107423b99fda94bf4af61efac
 * Created by Administrator on 2016/5/27.
 * 获取网络数据 并解析
 */
public class WeatherUtil {
    public static final String KEY = "929add6107423b99fda94bf4af61efac";
    public static final String TAG="djsnjs";
    static JSONObject jsonRealTime;
    static JSONObject jsonLife;
    static JSONArray jsonWeather;
    static JSONObject jsonPM25;

    public WeatherUtil(String city_name) throws IOException {
        getRequest(city_name);
    }
    /**
     * 根据城市名获取天气数据
     */
    private String getRequest(String city_name) throws IOException {
        String  rs = null;
        StringBuffer sb = new StringBuffer();
        String  url="http://op.juhe.cn/onebox/weather/query?";
        //拼接URL
        String uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("cityname", city_name)//城市名
                .appendQueryParameter("dtype", "")//返回数据类型
                .appendQueryParameter("key", KEY)//APIKEY
                .build().toString();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL uri1=new URL(uri);
            //打开连接  获得HttpURLConnection的对象,获取网络数据
            connection = (HttpURLConnection) uri1.openConnection();
            connection.setConnectTimeout(5000);//超时时间
            connection.setRequestMethod("GET");//请求类型
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len=reader.readLine())!=null){
                sb.append(len).append("\n");
            }
             rs=sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
            reader.close();
        }
        getWeatherDataFromJson(rs, city_name);
        return rs;
    }
    /**
     * 初步解析json数据
     */
    private void getWeatherDataFromJson(String jsonStr, String cityname){
        try {
            JSONObject json=new JSONObject(jsonStr);
            String str=json.getString("reason");
            int    code = json.getInt("error_code");
            if (str.equals("成功") || str.equals("successed!") || code == 0) {//判断获取数据是否成功
                JSONObject jsonResult = json.getJSONObject("result");//json返回数据
                JSONObject jsonData = jsonResult.getJSONObject("data");//json返回数据
                //实时数据
                jsonRealTime = jsonData.getJSONObject("realtime");
                //生活指数
                jsonLife = jsonData.getJSONObject("life");
                //一周天气
                jsonWeather = jsonData.getJSONArray("weather");
                //PM25
               jsonPM25 = jsonData.getJSONObject("pm25");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取实时数据
     */
    public  WeatherBean getRealTime() {
        String time        = null;
        String data        = null;
        String city   = null;
        String week        = null;
        String moon        = null;//农历
        String direct      = null;
        String power       = null;
        String humidity    = null;
        String info        = null;//天气
        String temperature = null;
        int    id          = -1;

        try {
            JSONObject wind=jsonRealTime.getJSONObject("wind");
            direct=wind.getString("direct");
            power=wind.getString("power");

            time=jsonRealTime.getString("time");
            JSONObject weather=jsonRealTime.getJSONObject("weather");
            humidity=weather.getString("humidity");
            id=weather.getInt("img");
            info=weather.getString("info");
            temperature=weather.getString("temperature");

            data=jsonRealTime.getString("data");
            city=jsonRealTime.getString("city");
            Object week_object = jsonRealTime.get("week");
            week = getWeek(week_object);
            moon=jsonRealTime.getString("moon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       WeatherBean bean=new WeatherBean(direct, power, time, humidity, info
               , temperature, data, city, week, moon, id);
        return bean;
    }
    /**
     * 根据获得星期的小写转换大写
     */
    private String getWeek(Object week_object) {
        String week = null;
        if (week_object instanceof Integer) {
            int week_int = (int) week_object;
            switch (week_int) {
                case 1:
                    week = "一";
                    break;
                case 2:
                    week = "二";
                    break;
                case 3:
                    week = "三";
                    break;
                case 4:
                    week = "四";
                    break;
                case 5:
                    week = "五";
                    break;
                case 6:
                    week = "六";
                    break;
                case 7:
                    week = "日";
                    break;
            }
        } else {
            week = week_object.toString();
        }
        return week;
    }
    /**
     * 解析生活指数详细数据
     */
    public  List<LifeBean> getJsonLife() throws JSONException {
        LifeBean lifeBean;
        List<LifeBean> been=new ArrayList<>();
        String kongtiao;
        String yundong;
        String ziwaixian;
        String ganmao;
        String xiche;
        String wuran;
        String chuanyi;

        lifeBean=getString("kongtiao");
        lifeBean.title="空调指数";
        been.add(lifeBean);
        lifeBean=getString("yundong");
        lifeBean.title="运动指数";
        been.add(lifeBean);
        lifeBean=getString("ziwaixian");
        lifeBean.title="紫外线指数";
        been.add(lifeBean);
        lifeBean=getString("ganmao");
        lifeBean.title="感冒指数";
        been.add(lifeBean);
        lifeBean=getString("xiche");
        lifeBean.title="洗车指数";
        been.add(lifeBean);
        lifeBean=getString("wuran");
        lifeBean.title="污染指数";
        been.add(lifeBean);
        lifeBean=getString("chuanyi");
        lifeBean.title="穿衣指数";
        been.add(lifeBean);

        return been;
    }
    /**
     * 根据KEY 获取生活指数详细信息
     */
    private  LifeBean getString(String key) throws JSONException {
        LifeBean bean;

            JSONObject info=jsonLife.getJSONObject("info");
            JSONArray jsonarray=info.getJSONArray(key);
            bean=new LifeBean(jsonarray.getString(0),jsonarray.getString(1));
        return bean;
    }

    /**
     * 解析一周天气详细数据
     */
    public  List<WeatherInfoBean> getJsonWeather() {
        String date              = null;
        String weather_day       = null;//天气
        String temperature_day   = null;//温度
        String direct_day        = null;//风向
        String power_day         = null;//风级
        String sun_up            = null;//太阳升起
        String weather_night     = null;//天气
        String temperature_night = null;//温度
        String direct_night      = null;//风向
        String power_night       = null;//风级
        String sun_down          = null;//降落
        String week              = null;//星期
        String moon              = null;//农历
        int    id_day            = -1;
        int    id_night          = -1;

        List<WeatherInfoBean> bean=new ArrayList<>();//读取一周天气集合
        //for循环遍历读取信息
        for (int i = 0; i <jsonWeather.length() ; i++) {
            try {
                JSONObject json=jsonWeather.getJSONObject(i);
                date=json.getString("date");
                JSONObject info=json.getJSONObject("info");
                JSONObject night=info.getJSONObject("night");
                JSONObject day=info.getJSONObject("day");
                weather_day=day.getString(String.valueOf(1));
                temperature_day = day.getString(String.valueOf(2));
                direct_day = day.getString(String.valueOf(3));
                power_day = day.getString(String.valueOf(4));
                sun_up = day.getString(String.valueOf(5));
                weather_night = night.getString(String.valueOf(1));
                temperature_night = night.getString(String.valueOf(2));
                direct_night = night.getString(String.valueOf(3));
                power_night = night.getString(String.valueOf(4));
                sun_down = night.getString(String.valueOf(5));
                week = json.getString("week");
                moon = json.getString("nongli");
                id_day = day.getInt(String.valueOf(0));
                id_night = night.getInt(String.valueOf(0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            WeatherInfoBean infoBean=new WeatherInfoBean(date, weather_day, temperature_day,
                    direct_day, power_day, sun_up, weather_night, temperature_night,
                    direct_night, power_night, sun_down, week, moon, id_day, id_night);
            bean.add(infoBean);
        }
        return bean;
    }
    /**
     * 解析PM25详细数据
     */
    public  PMBean getJsonPM25() {
        String city=null;//城市
        String dateTime=null;//发布时间
        String curPm=null;//污染指数
        String pm25=null;
        String pm10=null;
        String quality=null;//污染等级
        String des=null;//详细介绍
        try {
            JSONObject json=jsonPM25.getJSONObject("pm25");
            curPm = json.getString("curPm");
            pm25 = json.getString("pm25");
            pm10 = json.getString("pm10");
            quality = json.getString("quality");
            des = json.getString("des");
            city = jsonPM25.getString("city");
            dateTime = jsonPM25.getString("dateTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PMBean bean = new PMBean(city, dateTime, curPm, pm25, pm10, quality, des);
        return bean;
    }
}
