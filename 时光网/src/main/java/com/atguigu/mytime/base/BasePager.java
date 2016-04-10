package com.atguigu.mytime.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/4/7.
 * 基类
 */
public abstract class BasePager {
    public Activity mactivity;
    public View rootview;
    public boolean isCreate=true;
    public BasePager(Activity mactivity) {
        this.mactivity=mactivity;
        rootview=initView();
    }

    /**
     * 子类实现 返回一个View对象
     * @return
     */
    public  abstract View initView() ;

    /**
     * 初始化数据
     */
    public void initData(){}
}
