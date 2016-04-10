package com.atguigu.mytime.discoverpagers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mytime.R;
import com.atguigu.mytime.Utils.NetUri;
import com.atguigu.mytime.adapter.CommentAdapter;
import com.atguigu.mytime.base.BasePager;
import com.atguigu.refreshlistview.RefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 影评
 * Created by Administrator on 16-4-8.
 */
public class Commentpager extends BasePager {
    private static final String TAG = Commentpager.class.getSimpleName();
    @ViewInject(R.id.listview_comment)
    private RefreshListView listview_comment;
    @ViewInject(R.id.prevue_head_icon)
    private ImageView prevue_head_icon;//头部的图片
    @ViewInject(R.id.headview_title)
    private TextView headview_title;//头部预告片的题目
    @ViewInject(R.id.top_small)
    private ImageView top_small;//头部的小图片
    @ViewInject(R.id.headview_movename)
    private TextView headview_movename;//电影名称
    private JSONObject review;
    private List<JSONObject> commentInfo;//列表信息集合
    private CommentAdapter adapter;
    public Commentpager(Activity mactivity, JSONObject review) {
        super(mactivity);
        this.review=review;
    }

    @Override
    public View initView(){
        View view = View.inflate(mactivity,R.layout.listview_comment, null);
        x.view().inject(this,view);
        //listView的头布局
        View headview = View.inflate(mactivity,R.layout.comment_headview, null);
        x.view().inject(this,headview);
        //给listView加载头部
        listview_comment.addTopNewsView(headview);
        return view;
    }

    private void initHeadview(){
        x.image().bind(prevue_head_icon,review.optString("imageUrl"));
        headview_movename.setText(review.optString("movieName"));
        headview_title.setText(review.optString("title"));
        x.image().bind(top_small, review.optString("posterUrl"));

    }

    @Override
    public void initData() {
        super.initData();
        //初始化头部
        initHeadview();
        //请求网络数据
        getDatafromNet();

    }

    private void getDatafromNet() {
        RequestParams params=new RequestParams(NetUri.COMMENT_LIST);
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result){
                //获取数据成功，解析数据
                Log.e(TAG, "result" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析数据
     * @param result
     */
    private void processData(String result){
        parseJsonData(result);//解析数据成功
        //初始化adapter
        adapter=new CommentAdapter(mactivity,commentInfo);

        //显示列表
        listview_comment.setAdapter(adapter);

    }

    private void parseJsonData(String result) {
//获取的是json数组，不能生成实体类对象了，所以用手动解析json
        try {
            //获取json数据
            JSONArray jsonArray = new JSONArray(result);
            commentInfo = new ArrayList<>();
            //遍历json数组
            for(int i = 0; i < jsonArray.length();i++){
                //获取每一个item的信息
                JSONObject object= (JSONObject)jsonArray.get(i);
                //将数据装入集合中
                commentInfo.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
