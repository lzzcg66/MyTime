package com.atguigu.mytime.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MessageUtils {
     public static void showMessage(Context context,String mss){
         Toast.makeText(context,mss,Toast.LENGTH_LONG).show();
    }
}
