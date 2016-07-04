package com.fanfan.feicui.mywidget.bean;

/**
 * Created by Administrator on 2016/5/27.
 */
public class LifeBean {
    public String title;//生活指数类型
    public String exp;//指数
    public String content;//详细介绍

    public LifeBean(String exp, String content) {
        this.exp = exp;
        this.content = content;
    }
}
