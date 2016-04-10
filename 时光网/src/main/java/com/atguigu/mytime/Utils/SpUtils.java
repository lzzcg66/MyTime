package com.atguigu.mytime.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/3/25.
 */
public class SpUtils {
    public static final String GUIDE ="guide";//标识是否进入主页面
    /*public static final String URL = URLPath.WEB_SERVICE_URL;
    public static final String READ_ARRAY_ID = "read_news_id_array_key";//保存点击过的item的id
    public static final String PHOTOS_IMAGE = URLPath.PHOTO_IMAGE_URL;*/
    private static SpUtils spUtils;
    private static SharedPreferences sp;
    public SpUtils() {

    }
    /*
    静态创建对象的方法
     */
    public static  SpUtils getInitialize(Context context){
        if(spUtils==null){
            //设置保存数据的存储发方式和文件名称
            sp=context.getSharedPreferences("beijinnews",Context.MODE_PRIVATE);
            spUtils=new SpUtils();
        }
        return spUtils;
    }
    /**
     * 保存数据的方法(根据不同的参数类型 保存不同的数据)
     */
    public boolean save(String key,Object value){
        if(value instanceof String){
            sp.edit().putString(key, (String) value).commit();
        }else if(value instanceof Integer){
            sp.edit().putInt(key, (Integer) value).commit();
        }else if(value instanceof Boolean){
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }else if(value instanceof Double){
            sp.edit().putFloat(key, (Float) value).commit();
        }
        return false;
    }
    /**
     * 读取保存的数据
     */
    public <T> T getValue(String key,Object value ){
        T t=null;
        if(value instanceof String ||value==null){//若value为空 就返回String类型
            t= (T) sp.getString(key, (String) value);
        }else if(value instanceof Integer){
           Integer v=sp.getInt(key, (Integer) value);//必须把结果转换为对象类型
            t= (T) v;
        }else if(value instanceof Double){
            Float v=sp.getFloat(key, (Float) value);
            t= (T) v;
        }else if(value instanceof Boolean){
            Boolean v=sp.getBoolean(key, (Boolean) value);
            t= (T) v;
        }
        return  t;
    }

    /**
     * 删除数据
     */
    public void delete(String key){
        sp.edit().remove(key).commit();
    }
    /**
     * 使用文件存储json数据
     */
    public  String getJsonData(String key){
        String result="";//默认
        //判断SD卡是否处于挂载状态
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                //文件的名称
                String fileName = MD5Encoder.encode(key);
                //如果SD卡处于挂载状态就用SD卡存储
                File file = new File(Environment.getExternalStorageDirectory()+"/MyTime",fileName);
                if(file.exists()) {
                    //读取数据
                    FileInputStream fis = new FileInputStream(file);
                    int len=0;
                    byte[] buffer = new byte[1024];
                   //将读取的内容转换成字符串
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    while ((len = fis.read(buffer)) != -1) {
                        bos.write(buffer,0,len);
                    }
                    fis.close();
                    bos.close();
                    result=bos.toString();
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //否则使用sp存储
            return getValue(key,"");
        }
return result;
    }
    /**
     * 将获取的json数据保存到文件中
     */
    public void saveJson(String key,String value){
        //判断SD卡是否处于挂载状态
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try{
            //文件名称
                String fileName = MD5Encoder.encode(key);
                //目录
                File file = new File(Environment.getExternalStorageDirectory() + "/MyTime", fileName);
                //判断目录是否存在
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                //判断文件是否存在
                if(!file.exists()) {
                    file.createNewFile();
                }
                //存储数据
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(value.getBytes());
                fos.flush();//强制写入
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //使用sp存储
            save(key, value);
        }

    }
}
