package com.hongri.recyclerview.utils;

import android.util.Log;

/**
 * @author：zhongyao on 2016/6/29 16:31
 * @description:Log工具类
 */
public class Logger {

    private static final String TAG = "yao";

    public static void d(String msg){
        if (msg !=null && !msg.equals("")){
            Log.d(TAG,msg);
        }
    }

    public static void e(String msg){
        if (msg !=null && !msg.equals("")){
            Log.e(TAG,msg);
        }
    }
}

