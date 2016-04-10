package com.atguigu.mytime.discoverpagers;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.mytime.base.BasePager;

import org.json.JSONObject;

/**
 * Created by Administrator on 16-4-8.
 */
public class Newspager extends BasePager {
    private TextView textView;
    private JSONObject topNews;
    public Newspager(Activity mactivity, JSONObject topNews) {
        super(mactivity);
        this.topNews=topNews;

    }

    /**
     * 初始化视图
     * @return
     */
    @Override
    public View initView() {
        textView = new TextView(mactivity);
        textView.setTextSize(30);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        //initData();
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("新闻");
    }
}
