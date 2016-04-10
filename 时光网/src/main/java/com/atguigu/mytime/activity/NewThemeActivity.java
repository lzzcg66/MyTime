package com.atguigu.mytime.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.atguigu.mytime.R;

public class NewThemeActivity extends Activity {
    private ImageView iv_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_theme);
        iv_theme = (ImageView)findViewById(R.id.iv_theme);
//        loadeImager();
    }

//    private void loadeImager() {
//        x.image().bind(iv_theme,);
//    }
}
