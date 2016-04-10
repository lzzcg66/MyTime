package com.atguigu.mytime.pager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mytime.R;
import com.atguigu.mytime.Utils.NetUri;
import com.atguigu.mytime.base.BasePager;
import com.atguigu.mytime.discoverpagers.Commentpager;
import com.atguigu.mytime.discoverpagers.Newspager;
import com.atguigu.mytime.discoverpagers.PrevuePager;
import com.atguigu.mytime.discoverpagers.Ranklistpager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 * 发现
 */
public class DiscoverPager extends BasePager{
    private JSONObject topNews;
    private JSONObject topPrevue;
    private JSONObject topRanklis;
    private JSONObject topComment;
    private static final String TAG = DiscoverPager.class.getSimpleName();
    //发现界面的四个详情界面的集合
    private List<BasePager> pagers;
    private DiscoverAdapter adapter;
    private TabLayout tab_discover;
    private ViewPager viewpager_discover;


    public DiscoverPager(Activity mactivity) {
        super(mactivity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.discover_page, null);
        tab_discover = (TabLayout) view.findViewById(R.id.tab_discover);
        viewpager_discover = (ViewPager) view.findViewById(R.id.viewpager_discover);

        return view;
    }

    /**
     * 初始化四个详情页面
     */
    private void initPager() {
        pagers=new ArrayList<>();
        Log.e(TAG,(topPrevue==null)+"");
        pagers.add(new Newspager(mactivity,topNews));//新闻
        pagers.add(new PrevuePager(mactivity,topPrevue));//预告片
        pagers.add(new Ranklistpager(mactivity,topRanklis));//排行榜
        pagers.add(new Commentpager(mactivity,topComment));//影评



    }

    @Override
    public void initData(){
        super.initData();

        //联网请求四个页面的Top信息
        getTopInfoFromnet();

    }
    /**
     * 联网请求四个页面的Top信息
     */
    private void getTopInfoFromnet(){

        RequestQueue requestQueue = Volley.newRequestQueue(mactivity);
        StringRequest request=new StringRequest(Request.Method.GET,NetUri.DISCOVER_TOP,new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
            //获取数据
                processJson(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        }){
                //解决乱码问题

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response){
                try {
                    String parsed = new String(response.data,"UTF-8");

                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        //将请求添加到队列中
        requestQueue.add(request);
    }

    /**
     * 解析json
     * @param result
     */
    private void processJson(String result){
        parseJsonData(result);
        //初始化详细页面
        initPager();
        adapter=new DiscoverAdapter();
        viewpager_discover.setAdapter(adapter);
        tab_discover.setupWithViewPager(viewpager_discover);
        //设置滚动
        tab_discover.setTabMode(TabLayout.MODE_FIXED);


    }

    private void parseJsonData(String result){
        //获取一个json对象
        try {
//            result=result.replaceAll("\ufeff","");
            JSONObject jsonobject = new JSONObject(result);
            //顶部的新闻信息
             topNews = jsonobject.optJSONObject("news");
            //顶部的预告片信息
             topPrevue = jsonobject.optJSONObject("trailer");
            //顶部的排行榜
             topRanklis = jsonobject.optJSONObject("topList");
            //顶部的影评
             topComment = jsonobject.optJSONObject("review");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    class DiscoverAdapter extends PagerAdapter {
        public String[] titles = {"新闻", "预告片", "排行榜", "影评"};


        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }




        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager itemPage = pagers.get(position);

            itemPage.initData();
            container.addView(itemPage.rootview);
            return itemPage.rootview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
