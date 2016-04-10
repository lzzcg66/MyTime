package com.atguigu.mytime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.atguigu.mytime.R;
import com.atguigu.mytime.Utils.SpUtils;

/**
 * 主页面
 */
public class WelcomActivity extends Activity {
    private RelativeLayout rl_welcom;
    private Animation animation;
    private boolean value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        rl_welcom = (RelativeLayout)findViewById(R.id.rl_welcom);
        //判断是否进入过引导页面
        value = SpUtils.getInitialize(this).getValue(SpUtils.GUIDE, false);
        initAnim();
        setAnimListener();
    }



    private void initAnim() {
        animation = AnimationUtils.loadAnimation(this, R.anim.welcom_anim);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        rl_welcom.startAnimation(animation);
    }
    private void setAnimListener() {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(value) {
                    //如果进入过引导页面
                    startActivity(new Intent(WelcomActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(WelcomActivity.this, GuideActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
