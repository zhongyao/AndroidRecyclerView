package com.hongri.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhongyao on 16/11/30.
 *
 * onLayout方法是ViewGroup中子View的布局方法，用于放置子View的位置。
 * 放置子View很简单，只需在重写onLayout方法，然后获取子View的实例，调用子View的layout方法实现布局。
 */

public class CustomViewGroup extends ViewGroup {
    // 子View的水平间隔
    private int ChildPadding = 20;

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 动态获取子View实例
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            // 放置子View，宽高都是100
            view.layout(l+10,t+10,l+300,b+300);
            l+=300+ ChildPadding;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //调用ViewGroup类中测量子类的方法
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //调用View类中默认的测量方法
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
