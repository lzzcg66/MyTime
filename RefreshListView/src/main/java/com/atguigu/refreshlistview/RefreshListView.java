package com.atguigu.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 作者：杨光福 on 2016/3/28 10:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：自定义带下拉刷新的ListView
 */
public class RefreshListView extends ListView {

    /**
     * 顶部轮播图部分：下拉刷新空间部分和ViewPager部分
     */
    private LinearLayout topnews;

    private ImageView iv_header_refresh;
    private ProgressBar pb_header_refresh;

    private TextView tv_header_status;
    private TextView tv_header_time;
    private View pullDownRefresh;

    /**
     * 下拉刷新控件的高
     */
    private int pullDownRefreshHeight;


    private float startY;
    /**
     * 顶部轮播图部分
     */
    private View topViewPager;

    /**
     * ListView在屏幕上y轴的坐标
     */
    private int mListViewOnScreenY = -1;


    /**
     * 下拉刷新状态
     */
    public static final int PULL_DOWN_REFRESH = 0;


    /**
     * 手松刷新状态
     */
    public static final int RELEASE_REFRESH = 1;


    /**
     * 正在刷新状态
     */
    public static final int REFRESHING = 2;

    private int currentStatus = PULL_DOWN_REFRESH;

    private Animation upAnimation;

    private Animation donwnAnimation;

    /**
     * 加载更多的视图
     */
    private View footView;

    private int footViewHeight;

    private boolean isLoadMore = false;


    public RefreshListView(Context context) {
        super(context);
        initHeaderView(context);
        initFooterView(context);
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        donwnAnimation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        donwnAnimation.setDuration(500);
        donwnAnimation.setFillAfter(true);


    }

    /**
     * 状态还原
     *
     * @param success
     */
    public void onFinishRefresh(boolean success) {
        if(isLoadMore){
            isLoadMore = false;
            footView.setPadding(0,-footViewHeight,0,0);

        }else{
            currentStatus = PULL_DOWN_REFRESH;
            iv_header_refresh.setVisibility(VISIBLE);
            iv_header_refresh.clearAnimation();

            pullDownRefresh.setPadding(0, -pullDownRefreshHeight, 0, 0);
            tv_header_status.setText("下拉刷新...");


        }


    }



    /**
     * 当刷新数据的时候，会回调这个接口中对应的方法
     */
    public interface OnRefreshListener {

        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();

        /**

         当上拉，并且滑动的最后一条数据的时候，回调这个方法
         */
        public void onLoadMore();

    }

    private OnRefreshListener onRefreshListener;

    /**
     * 设置刷新的监听 ,afu
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView(context);
        initFooterView(context);
        initAnimation();
    }

    /**
     * 初始化加载更多的布局
     *
     * @param context
     */
    private void initFooterView(Context context) {
        footView = View.inflate(context, R.layout.refresh_footerview, null);
        footView.measure(0,0);
        footViewHeight = footView.getMeasuredHeight();

        //默认设置隐藏
        footView.setPadding(0,-footViewHeight,0,0);

//        footView.setPadding(10,10,10,10);

        addFooterView(footView);//添加到ListView中


        //设置监听ListView的滚动
        setOnScrollListener(new MyOnScrollListener());
    }

    class MyOnScrollListener  implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当惯性滚动或者静止，并且是滑动到最后一个的时候
            if(scrollState ==SCROLL_STATE_IDLE||scrollState ==SCROLL_STATE_FLING){
                if((getAdapter().getCount()-1)==getLastVisiblePosition()){

                    //显示加载更多空间
                    footView.setPadding(10,10,10,10);

                    //设置状态
                    isLoadMore = true;


//                    setSelection(getCount());//定位到最后一个

                    //回调接口
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoadMore();
                    }




                }
            }


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
    /**
     * 初始化下拉刷新空间
     *
     * @param context
     */
    private void initHeaderView(Context context) {
        topnews = (LinearLayout) View.inflate(context, R.layout.refresh_headerview, null);

        iv_header_refresh = (ImageView) topnews.findViewById(R.id.iv_header_refresh);
        tv_header_status = (TextView) topnews.findViewById(R.id.tv_header_status);


        /**
         * 下拉刷新空间
         */
        pullDownRefresh = topnews.findViewById(R.id.ll_pull_down_refresh);

        pullDownRefresh.measure(0, 0);//先测量

        pullDownRefreshHeight = pullDownRefresh.getMeasuredHeight();//才会得到高度


//        view.setPadding(0,-控件的高，0,0);//下拉刷新控件完全隐藏
//        view.setPadding(0,0,0,0);//下拉刷新控件完全显示
//        view.setPadding(0,控件的高，0,0);//下拉刷新控件2倍完全显示

        pullDownRefresh.setPadding(0, -pullDownRefreshHeight, 0, 0);


        //添加到ListView的头部

        addHeaderView(topnews);


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.在移动的时候记录 endY
                float endY = ev.getY();
                //3.计算偏移量
                float distanceY = endY - startY;

                //当下拉刷新的时候，不让再刷新
                if (currentStatus == REFRESHING) {
                    break;
                }

                //当顶部轮播完全显示了，才会让下拉刷新空间开始显示
                boolean isDisplayTopNews = isDisplayTopNews();

                if (!isDisplayTopNews) {

                    break;

                }

                if (distanceY > 0) {

                    float padintTop = -pullDownRefreshHeight + distanceY;

//                    view.setPadding(0,-控件的高，0,0);//下拉刷新控件完全隐藏
//                    view.setPadding(0,0，0,0);//下拉刷新控件完全显示
//                    view.setPadding(0,控件的高，0,0);//下拉刷新控件2倍完全显示

                    if (padintTop >= 0 && currentStatus != RELEASE_REFRESH) {
                        currentStatus = RELEASE_REFRESH;
                        System.out.println("手松刷新...");
                        refreshHeanderViewStatu();
                    } else if (padintTop < 0 && currentStatus != PULL_DOWN_REFRESH) {
                        currentStatus = PULL_DOWN_REFRESH;
                        System.out.println("下拉刷新...");
                        refreshHeanderViewStatu();

                    }


                    pullDownRefresh.setPadding(0, (int) padintTop, 0, 0);//下拉刷新控件动态的隐藏和显示

                }
                break;
            case MotionEvent.ACTION_UP:

                if (currentStatus == PULL_DOWN_REFRESH) {
                    pullDownRefresh.setPadding(0, -pullDownRefreshHeight, 0, 0);//隐藏下拉刷新空间
                } else if (currentStatus == RELEASE_REFRESH) {
                    //切换成正在刷新状态
                    currentStatus = REFRESHING;
                    pullDownRefresh.setPadding(0, 0, 0, 0);//隐藏下拉刷新空间
                    refreshHeanderViewStatu();

                    //回调接口请求网络
                    if (onRefreshListener != null) {
                        onRefreshListener.onPullDownRefresh();
                    }


                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshHeanderViewStatu() {
        switch (currentStatus) {
            case PULL_DOWN_REFRESH://下拉刷新
                iv_header_refresh.startAnimation(donwnAnimation);
                tv_header_status.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH:// 手松刷新

                iv_header_refresh.startAnimation(upAnimation);
                tv_header_status.setText("手松刷新...");
                break;
            case REFRESHING: //正在刷新

                iv_header_refresh.clearAnimation();
                iv_header_refresh.setVisibility(View.GONE);//图片隐藏
                tv_header_status.setText("正在刷新...");
                pb_header_refresh.setVisibility(View.VISIBLE);


                break;

        }
    }

    /**
     * 判断顶部轮播图是否完全显示
     * 要用ListView和ViewPager部分比较
     *
     * @return 当ListView在屏幕上的Y轴坐标小于或者等于顶部轮播图在Y轴的坐标的时候，顶部轮播图完全显示了
     */
    private boolean isDisplayTopNews() {

        if(topViewPager != null){
            int[] location = new int[2];
            //得到ListView在屏幕上的Y轴坐标
            if (mListViewOnScreenY == -1) {
                this.getLocationOnScreen(location);
                mListViewOnScreenY = location[1];
            }

            //顶部ViewPager部分在屏幕上的Y轴坐标
            topViewPager.getLocationOnScreen(location);
            int mtopViewPagerOnScreenY = location[1];

//        if(mListViewOnScreenY <= mtopViewPagerOnScreenY){
//            return  true;
//        }else{
//            return  false;
//        }

            return mListViewOnScreenY <= mtopViewPagerOnScreenY;
        }

        return  true;


    }

    /**
     * 传入顶部轮播图部分
     *
     * @param headerView
     */
    public void addTopNewsView(View headerView) {
        if(topnews != null){
            this.topViewPager = headerView;

            topnews.addView(topViewPager);//添加到外面的线性布局里面，形成一个整体
        }


    }
}
