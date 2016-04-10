package com.atguigu.mytime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.atguigu.mytime.R;
import com.atguigu.mytime.Utils.SpUtils;

public class GuideActivity extends Activity {
    private ViewPager guide_pager;
    private Button guide_btn;
    private int[] img={R.drawable.lead_bg1,R.drawable.lead_bg2,R.drawable.lead_bg3,R.drawable.lead_bg4};
    private int[] index_img={R.drawable.lead_bg1_iv,R.drawable.lead_bg2_iv,R.drawable.lead_bg3_iv,R.drawable.lead_bg4_iv};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guide_pager = (ViewPager)findViewById(R.id.guide_pager);
        guide_btn = (Button)findViewById(R.id.guide_btn);
        guide_pager.setAdapter(new MyPagerAdapter());
        guide_pager.addOnPageChangeListener(new MyOnPageChangeListener());
        //点击进入主页面
        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后就保存一个标识
                SpUtils.getInitialize(GuideActivity.this).save(SpUtils.GUIDE,true);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position==img.length-1){
                guide_btn.setVisibility(View.VISIBLE);
            }else {
                guide_btn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=View.inflate(GuideActivity.this,R.layout.guide_pager,null);
            ImageView guide_image= (ImageView) view.findViewById(R.id.guide_image);
            ImageView guide_index_img= (ImageView) view.findViewById(R.id.guide_index_img);

            guide_image.setBackgroundResource(img[position]);
            guide_index_img.setBackgroundResource(index_img[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }
    }
}
