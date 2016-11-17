package com.hongri.recyclerview.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author：zhongyao on 2016/6/30 11:41
 * @description:
 */
public class ToastUtil extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtil(Context context) {
        super(context);
    }

    public static void ShowBottomShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void ShowBottomShort(Context context,int id){
        Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
    }
    public static void ShowBottomLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

}
