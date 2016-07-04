package com.fanfan.feicui.mywidget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanfan.feicui.mywidget.R;
import com.fanfan.feicui.mywidget.bean.WeatherInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 *天气适配器
 */
public class WeatherAdapter extends BaseAdapter{
    LayoutInflater        inflater;
    List<WeatherInfoBean> data;
    public WeatherAdapter(Context context, List<WeatherInfoBean> data) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public WeatherInfoBean getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHaber haber;
        if (view == null) {
            haber = new ViewHaber();
            view = inflater.inflate(R.layout.list_weather, null);
            haber.data = (TextView) view.findViewById(R.id.tv_date);
            haber.weather = (TextView) view.findViewById(R.id.tv_weather);
            haber.temperature = (TextView) view.findViewById(R.id.tv_temperature);
            haber.direct = (TextView) view.findViewById(R.id.tv_direct);
            haber.power = (TextView) view.findViewById(R.id.tv_power);
            haber.week = (TextView) view.findViewById(R.id.tv_week);
            view.setTag(haber);
        } else {
            haber = (ViewHaber) view.getTag();
        }
        haber.data.setText(getItem(i).data);
        haber.weather.setText(getItem(i).weather_day);
        haber.temperature.setText("温度" + getItem(i)
                .temperature_night + "~" + getItem(i).temperature_day + "℃");
        haber.direct.setText(getItem(i).direct_day);
        haber.power.setText(getItem(i).power_day);
        haber.week.setText("星期" + getItem(i).week);
        return view;
    }
    class ViewHaber {
        TextView data;//日期
        TextView weather;//天气
        TextView temperature;//温度
        TextView direct;//风向
        TextView power;//风级
        TextView week;//星期
    }
}
