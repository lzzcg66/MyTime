package com.atguigu.mytime.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mytime.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 16-4-9.
 */
public class CommentAdapter extends BaseAdapter {
    private List<JSONObject> commentInfo;
    private Activity activity;
    public CommentAdapter(Activity mactivity, List<JSONObject> commentInfo) {
        this.commentInfo=commentInfo;
        this.activity=mactivity;
    }

    @Override
    public int getCount() {
        return commentInfo.size();
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
        ViewHolder viewHolder=null;
        if(convertView==null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(activity, R.layout.comment_list_item, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_summary = (TextView) convertView.findViewById(R.id.tv_summary);
            viewHolder.iv_userimage = (ImageView) convertView.findViewById(R.id.iv_userimage);
            viewHolder.nick_name = (TextView) convertView.findViewById(R.id.nick_name);
            viewHolder.relatedObj_tltle = (TextView) convertView.findViewById(R.id.relatedObj_tltle);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rating);
            viewHolder.relatedObj_image = (ImageView) convertView.findViewById(R.id.relatedObj_image);
//设置关联
            convertView.setTag(viewHolder);
        }else{
            //复用
            viewHolder= (ViewHolder) convertView.getTag();
        }
        JSONObject item = commentInfo.get(position);
        //装配数据
        viewHolder.tv_title.setText(item.optString("title"));
        viewHolder.tv_summary.setText(item.optString("summary"));
        /**
         * 使用glide联网加载图片;并将图片设置成圆形图片
         */
        final ViewHolder finalViewHolder = viewHolder;
        Glide.with(activity)
                .load(item.optString("userImage"))
                .asBitmap().centerCrop()
        .into(new BitmapImageViewTarget(finalViewHolder.iv_userimage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                finalViewHolder.iv_userimage.setImageDrawable(circularBitmapDrawable);
            }
        });
        viewHolder.nick_name.setText(item.optString("nickname"));
        JSONObject relatedObj = item.optJSONObject("relatedObj");
        viewHolder.relatedObj_tltle.setText("《"+relatedObj.optString("title")+"》");
        viewHolder.rating.setText(item.optString("rating"));
        /**
         * 使用glide联网加载图片
         */
        Glide.with(activity)
                .load(relatedObj.optString("image"))
                .into(viewHolder.relatedObj_image);
        return convertView;

    }
    class ViewHolder{
        TextView tv_title;//标题
        TextView tv_summary;//详情
        ImageView iv_userimage;//评论者头像
        TextView nick_name;//评论者
        TextView relatedObj_tltle;//评论的哪部影片
        TextView rating;//评分
        ImageView relatedObj_image;
        
    }
}
