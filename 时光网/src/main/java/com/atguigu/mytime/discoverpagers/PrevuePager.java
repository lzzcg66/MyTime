package com.atguigu.mytime.discoverpagers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mytime.R;
import com.atguigu.mytime.Utils.NetUri;
import com.atguigu.mytime.adapter.PrevueListAdapter;
import com.atguigu.mytime.base.BasePager;
import com.atguigu.refreshlistview.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 预告片
 * Created by Administrator on 16-4-8.
 */
public class PrevuePager extends BasePager {

    private static final String TAG = PrevuePager.class.getSimpleName();

    private JSONObject trailer;
    private PrevueListAdapter adapter;
    private RefreshListView listview_discover;
    private ImageView prevue_head_icon;//头部的图片
    private List<JSONObject> lists;
    private TextView headview_title;//头部预告片的题目


    public PrevuePager(Activity mactivity, JSONObject trailer) {
        super(mactivity);
        this.trailer = trailer;
    }

    @Override
    public View initView(){
        View view = View.inflate(mactivity,R.layout.listview_discover,null);
        listview_discover = (RefreshListView) view.findViewById(R.id.listview_discover);
        //listView的头布局
        View headview = View.inflate(mactivity,R.layout.prevue_headview,null);
        prevue_head_icon = (ImageView) headview.findViewById(R.id.prevue_head_icon);
        headview_title = (TextView) headview.findViewById(R.id.headview_title);
        //给listView加载头部
        listview_discover.addTopNewsView(headview);

        return view;
    }

    /**
     * 显示头部
     */
    private void initHeadview(){
        x.image().bind(prevue_head_icon,trailer.optString("imageUrl"));
        headview_title.setText(trailer.optString("title"));

    }

    @Override
    public void initData() {
        //初始化头部
        initHeadview();
        //联网加载数据
        String uri = NetUri.DISCOVER_LIST;
        RequestParams params = new RequestParams(uri);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求数据成功
                processJsonData(result);
                Log.e(TAG, "result==" + result);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "ex==" + ex);
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
     * 解析请求的json数据
     *
     * @param result
     */
    private void processJsonData(String result) {
        parseJsonData(result);//解析数据成功
        //初始化列表信息集合:即为listView中每个item的信息


        adapter = new PrevueListAdapter(mactivity, lists);
        listview_discover.setAdapter(adapter);


    }

    /**
     * 解析
     *
     * @param result
     */
    private void parseJsonData(String result) {
        //解析数据
        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray trailers = jsonObject.optJSONArray("trailers");
            lists = new ArrayList<>();
            //遍历数组
            for (int i = 0; i < trailers.length(); i++) {
                JSONObject item = (JSONObject) trailers.get(i);
                //添加到集合中
                lists.add(item);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
