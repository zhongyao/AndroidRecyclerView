package com.hongri.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Create by zhongyao on 2021/11/1
 * Description:
 */
public class SettingLayout extends LinearLayout {

    private final String TAG = "SettingLayout";

    public SettingLayout(Context context) {
        super(context);
    }

    public SettingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure ---> widthMeasureSpec:" + widthMeasureSpec + " heightMeasureSpec:" + heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged ---> w:" + w + " h:" + h + " oldw:" + oldw + " oldh:" + oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout ---> changed:" + changed + " left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
    }
}
