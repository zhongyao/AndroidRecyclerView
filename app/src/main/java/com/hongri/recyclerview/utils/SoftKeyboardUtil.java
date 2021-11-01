package com.hongri.recyclerview.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Create by zhongyao on 2021/8/2
 * Description: 软键盘工具类
 */
public class SoftKeyboardUtil {
    private static final String TAG = "SoftKeyboardUtil";

    /**
     * 【较可靠方案】判断输入法是否显示
     * @return
     */
    public static boolean isSoftShowing(Activity activity) {
        if (activity == null) {
            return false;
        }
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        Log.d(TAG, "screenHeight:" + screenHeight + " rect.bottom:" + rect.bottom);
        return screenHeight * 2 / 3 > rect.bottom;
    }


    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)， 不丢失焦点
     *
     * @param context
     * @param viewList viewList 中需要放的是当前界面所有触发软键盘弹出的控件对象
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 软键盘显示/隐藏[隐藏时调用会显示，显示时调用会隐藏]
     */
    public void hideShowKeyboard(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
     *
     * @param context
     * @param view
     */
    public void showViewKeyBoard(Context context, View view) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //显示
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        //强制隐藏键盘
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}