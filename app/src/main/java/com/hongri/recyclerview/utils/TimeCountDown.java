package com.hongri.recyclerview.utils;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Create by zhongyao on 2021/11/9
 * Description:
 */
public class TimeCountDown extends CountDownTimer {

    private final String TAG = "TimeCountDown";
    private ICountListener listener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d(TAG, "millisUntilFinished:" + millisUntilFinished);
        if (listener != null) {
            listener.onTick(millisUntilFinished);
        }

    }

    @Override
    public void onFinish() {
        Log.d(TAG, "finish");
        if (listener != null) {
            listener.onFinish();
        }
    }

    public void setListener(ICountListener countListener) {
        listener = countListener;
    }

    public interface ICountListener {
        void onTick(long millisUntilFinished);
        void onFinish();
    }
}
