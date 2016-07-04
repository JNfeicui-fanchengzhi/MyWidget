package com.fanfan.feicui.mywidget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanfan.feicui.mywidget.R;
import com.fanfan.feicui.mywidget.adapter.WeatherAdapter;
import com.fanfan.feicui.mywidget.bean.WeatherBean;
import com.fanfan.feicui.mywidget.bean.WeatherInfoBean;
import com.fanfan.feicui.mywidget.util.ImageUtil;
import com.fanfan.feicui.mywidget.util.WeatherUtil;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private       RelativeLayout mrlread;
    private       TextView       mtvcityname;
    private       TextView       mtvweek;
    private       TextView       mtvtemperature;
    private       TextView       mtvhumidity;
    private       TextView       mtvdirect;
    private       TextView       mtvpower;
    private       TextView       mtvdate;
    private       TextView       mtvmoon;
    private       TextView       mtvtime;
    private       ImageView      mImageView;
    private       TextView       mtvweather;
    private       ListView       mlvweather;
    public static WeatherUtil    mWeatherUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrlread = (RelativeLayout) findViewById(R.id.rl_real);
        mtvcityname = (TextView) findViewById(R.id.tv_city_name);//地区
        mtvweek = (TextView) findViewById(R.id.tv_week);//星期
        mtvtemperature = (TextView) findViewById(R.id.tv_temperature);//温度
        mtvhumidity = (TextView) findViewById(R.id.tv_humidity);//湿度
        mtvdirect = (TextView) findViewById(R.id.tv_direct);//风向
        mtvpower = (TextView) findViewById(R.id.tv_power);//风级
        mtvdate = (TextView) findViewById(R.id.tv_date);//日期
        mtvmoon = (TextView) findViewById(R.id.tv_moon);//农历日期
        mtvtime = (TextView) findViewById(R.id.tv_time);//更新时间
        mImageView = (ImageView) findViewById(R.id.imageView);
        mtvweather = (TextView) findViewById(R.id.tv_weather);//天气
        mlvweather = (ListView) findViewById(R.id.lv_weather);
        mrlread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        mlvweather.setOnItemClickListener(this);
        inotData();
    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myHandleMessage(msg);
        }
    };

    private void inotData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    mWeatherUtil = new WeatherUtil("济南");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                WeatherBean realTime = mWeatherUtil.getRealTime();
                Message     message  = new Message();
                message.what = 0;
                message.obj = realTime;
                handler.sendMessage(message);
                Message               message1 = new Message();
                List<WeatherInfoBean> data     = mWeatherUtil.getJsonWeather();
                message1.what = 1;
                message1.obj = data;
                handler.sendMessage(message1);
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /**根据点击item的id进行传参跳转*/
            Intent intent = new Intent(MainActivity.this,Main3Activity.class);
            intent.putExtra("id",i);
            startActivity(intent);
    }
    /**
     * 更新UI
     */
    protected void myHandleMessage(Message msg) {

        switch (msg.what) {
            case 0:
                mrlread.setBackgroundResource(R.color.weatherBackground);
                WeatherBean realTime = (WeatherBean) msg.obj;
                mtvdirect.setText(realTime.direct);//风向
                mtvpower.setText(realTime.power);//风级
                mtvtime.setText("更新时间" + realTime.time);//更新时间
                mtvhumidity.setText("相对湿度" + realTime.humidity + "%");//湿度
                mtvweather.setText(realTime.weather);//天气
                mtvtemperature.setText("温度" + realTime.temperature + "℃");//温度
                mtvdate.setText(realTime.date);//日期
                mtvcityname.setText(realTime.city);//地区
                mtvweek.setText("星期" + realTime.week);//星期
                mtvmoon.setText("农历" + realTime.moon);//农历日期
                mImageView.setImageResource(ImageUtil.getImageDay(realTime.id));
                break;
            case 1:
                List<WeatherInfoBean> data = (List<WeatherInfoBean>) msg.obj;
                WeatherAdapter adapter = new WeatherAdapter(getApplicationContext(), data);
                mlvweather.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}


