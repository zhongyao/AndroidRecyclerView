package com.hongri.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhongyao on 16/11/30.
 */

public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //调用ViewGroup类中测量子类的方法
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //调用View类中默认的测量方法
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
