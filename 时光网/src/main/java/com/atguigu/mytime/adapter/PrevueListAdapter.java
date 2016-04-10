package com.atguigu.mytime.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mytime.R;

import org.json.JSONObject;
import org.xutils.x;

import java.util.List;

/**
 * 预告片的listview的adapter
 * Created by Administrator on 16-4-8.
 */
public class PrevueListAdapter extends BaseAdapter{
    private Activity activity;
    private  List<JSONObject> trailers;
    public PrevueListAdapter(Activity mactivity,  List<JSONObject> trailers) {
        this.activity=mactivity;
        this.trailers=trailers;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView=View.inflate(activity, R.layout.prevue_list_item,null);
            holder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            //关联
            convertView.setTag(holder);
        }else{
            //复用
            holder= (ViewHolder) convertView.getTag();
        }
        //获取每个item的信息
        JSONObject itemInfo = trailers.get(position);
        //装配数据
        //设置图片的默认图片
        holder.item_icon.setImageResource(R.drawable.img_default_300x200);
        x.image().bind(holder.item_icon,itemInfo.optString("coverImg"));
        holder.tv_title.setText(itemInfo.optString("movieName"));
        holder.tv_desc.setText(itemInfo.optString("summary"));
        return convertView;
    }
    class ViewHolder{
        private ImageView item_icon;
        private TextView tv_title;//预告片标题
        private TextView tv_desc;//预告片的描述
    }
}
