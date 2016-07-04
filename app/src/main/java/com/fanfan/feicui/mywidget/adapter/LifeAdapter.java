package com.fanfan.feicui.mywidget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanfan.feicui.mywidget.R;
import com.fanfan.feicui.mywidget.bean.LifeBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class LifeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<LifeBean> data;

    public LifeAdapter(Context context, List<LifeBean> data) {
        inflater = LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public LifeBean getItem(int i) {
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
            view = inflater.inflate(R.layout.life_weather, null);
            haber.title = (TextView) view.findViewById(R.id.tv_title);
            haber.exp = (TextView) view.findViewById(R.id.tv_exp);
            haber.miute = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(haber);
        } else {
            haber = (ViewHaber) view.getTag();
        }
        haber.title.setText(getItem(i).title);
        haber.exp.setText(getItem(i).exp);
        haber.miute.setText(getItem(i).content);
        return view;
    }
    class ViewHaber {
        TextView title;
        TextView exp;
        TextView miute;
    }
}