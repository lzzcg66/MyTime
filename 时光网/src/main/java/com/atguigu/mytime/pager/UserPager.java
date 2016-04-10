package com.atguigu.mytime.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.mytime.base.BasePager;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UserPager extends BasePager {

    private TextView textView;

    public UserPager(Activity mactivity) {
        super(mactivity);
    }

    @Override
    public View initView() {
        textView = new TextView(mactivity);
        textView.setTextSize(30);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
    @Override
    public void initData() {
        super.initData();
        textView.setText("我的设置页面");
    }
}
