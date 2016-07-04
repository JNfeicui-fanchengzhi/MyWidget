package com.fanfan.feicui.mywidget.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanfan.feicui.mywidget.R;
import com.fanfan.feicui.mywidget.adapter.LifeAdapter;
import com.fanfan.feicui.mywidget.bean.LifeBean;
import com.fanfan.feicui.mywidget.bean.PMBean;
import com.fanfan.feicui.mywidget.bean.WeatherBean;
import com.fanfan.feicui.mywidget.util.ImageUtil;

import org.json.JSONException;

import java.util.List;

public class Main2Activity extends MainActivity {
//    public static WeatherUtil mWeatherUtil;

    private TextView  mtvcityname;//地区
    private TextView  mtvweek;//星期
    private TextView  mtvtemperature;//温度
    private TextView  mtvhumidity;//湿度
    private TextView  mtvdirect;//风向
    private TextView  mtvpower;//风级
    private TextView  mtvdate;//日期
    private TextView  mtvmoon;//农历日期
    private TextView  mtvtime;//更新时间
    private ImageView mImageView;
    private TextView  mtvweather;//天气

    private TextView  mtvtvcurpm;
    private TextView  mtvpm25;
    private TextView  mtvpm10;
    private TextView  mtvquality;
    private TextView  mtvdatatime;
    private TextView  mtvdes;
    private ListView mlvlife;
    List<LifeBean> data;
    LifeAdapter    adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mtvcityname=(TextView) findViewById(R.id.tv_city_name);//地区
        mtvweek=(TextView) findViewById(R.id.tv_week);//星期
        mtvtemperature=(TextView) findViewById(R.id.tv_temperature);//温度
        mtvhumidity=(TextView) findViewById(R.id.tv_humidity);//湿度
        mtvdirect=(TextView) findViewById(R.id.tv_direct);//风向
        mtvpower=(TextView) findViewById(R.id.tv_power);//风级
        mtvdate=(TextView) findViewById(R.id.tv_date);//日期
        mtvmoon=(TextView) findViewById(R.id.tv_moon);//农历日期
        mtvtime=(TextView) findViewById(R.id.tv_time);//更新时间
        mImageView= (ImageView) findViewById(R.id.imageView);
        mtvweather=(TextView) findViewById(R.id.tv_weather);//天气
        mtvtvcurpm= (TextView) findViewById(R.id.tv_cur_pm);
        mtvpm25= (TextView) findViewById(R.id.tv_pm25);
        mtvpm10= (TextView) findViewById(R.id.tv_pm10);
        mtvquality= (TextView) findViewById(R.id.tv_quality);
        mtvdatatime= (TextView) findViewById(R.id.tv_data_time);
        mtvdes= (TextView) findViewById(R.id.tv_des);
        mlvlife= (ListView) findViewById(R.id.lv_life);


            initData();

    }

    private  void initData() {
        WeatherBean realTime =mWeatherUtil.getRealTime();
        mtvcityname.setText(realTime.city);//地区
        mtvweek.setText("星期" +realTime.week);//星期
        mtvtemperature.setText("温度" +realTime.temperature+ "℃");//温度
        mtvhumidity.setText("相对湿度" +realTime.humidity+ "%");//湿度
        mtvdirect.setText(realTime.direct);//风向
        mtvpower.setText(realTime.power);//风级
        mtvdate.setText(realTime.date);//日期
        mtvmoon.setText("农历" +realTime.moon);//农历日期
        mtvtime.setText("更新时间" +realTime.time);//更新时间
        mImageView.setImageResource(ImageUtil.getImageDay(realTime.id));
        mtvweather.setText(realTime.weather);//天气
        PMBean bean = mWeatherUtil.getJsonPM25();
        mtvdatatime.setText("更新时间:" + bean.getDateTime());
        mtvtvcurpm.setText("污染指数:" + bean.getCurPm());
        mtvpm25.setText("PM2.5: " + bean.getPm25());
        mtvpm10.setText("PM10:  " + bean.getPm10());
        mtvdes.setText(bean.getDes());
        mtvquality.setText("污染等级:" + bean.getQuality());
        try {
            data= mWeatherUtil.getJsonLife();
            adapter = new LifeAdapter(getApplicationContext(), data);
            mlvlife.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
