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
public class Ranklistpager extends BasePager {
private TextView textView;
    private JSONObject topList ;
    public Ranklistpager(Activity mactivity, JSONObject topList) {
        super(mactivity);
        this.topList=topList;
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
        textView.setText("排行榜");
    }
}
