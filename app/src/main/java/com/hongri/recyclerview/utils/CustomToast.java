package com.hongri.recyclerview.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hongri.recyclerview.R;

/**
 * Create by zhongyao on 2021/12/1
 * Description: 自定义Toast
 */
public class CustomToast {

    private static final String TAG = "CustomToast";
    private boolean canceled = true;
    private Handler handler;
    private Toast toast;
    private TimeCount time;
    private TextView toast_content;

    public CustomToast(Context context, ViewGroup viewGroup) {
        this(context, viewGroup, new Handler());
    }

    public CustomToast(Context context, ViewGroup viewGroup, Handler handler) {
        this.handler = handler;

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, viewGroup);
        toast_content = (TextView) layout.findViewById(R.id.tvToastContent);
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
    }

    /**
     * @param text     要显示的内容
     * @param duration 显示的时间长
     *                 根据LENGTH_MAX进行判断
     *                 如果不匹配，进行系统显示
     *                 如果匹配，永久显示，直到调用hide()
     */
    public void show(String text, int duration) {
        time = new TimeCount(duration, 1000);
        toast_content.setText(text);
        if (canceled) {
            time.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏Toast
     */
    public void hide() {
        if (toast != null) {
            toast.cancel();
        }
        Log.d(TAG, "hide ---> canceled:" + canceled);
        canceled = true;
    }

    private void showUntilCancel() {
        Log.d(TAG, "canceled:" + canceled);
        if (canceled) {
            return;
        }
        toast.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d(TAG, "revoke -- showUntilCancel" + " isMainThread:" + (Thread.currentThread() == Looper.getMainLooper().getThread()));
                showUntilCancel();
            }
        }, 2000);
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); // 总时长,计时的时间间隔
        }

        @Override
        public void onFinish() { // 计时完毕时触发
            Log.d(TAG, "onFinish");
            hide();
        }

        @Override
        public void onTick(long millisUntilFinished) { // 计时过程显示
            Log.d(TAG, "millisUntilFinished:" + millisUntilFinished);
        }

    }

}
