package com.hongri.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author：zhongyao on 2016/11/17 18:22
 * @description:View的工作原理
 */

public class ViewPrinciple extends View {

    public ViewPrinciple(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
