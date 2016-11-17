package com.hongri.recyclerview.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.hongri.recyclerview.R;

/**
 * 下拉刷新标准控件，使用时将其作为布局的父容器
 * 增加滑动刷新时间的判断
 * Created by 1 on 2015/4/30
 */
public class CompatSwipeRefreshLayout extends SwipeRefreshLayout {

    private ScrollCompat mCompat;

    private int mTouchSlop;
    private float mPrevX;

    private float orgPosX;

    public CompatSwipeRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public CompatSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setRingColor(int... colorResIds){
        setColorSchemeResources(colorResIds);
    }

    public void setScrollCompat(ScrollCompat compat) {
        mCompat = compat;
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setColorSchemeResources(R.color.SwipeRefrshRingColor2, R.color.SwipeRefrshRingColor1, R.color.SwipeRefrshRingColor2, R.color.SwipeRefrshRingColor1);
    }

    @Override
    public boolean canChildScrollUp() {
        return mCompat != null ? mCompat.canChildScrollUp() : super.canChildScrollUp();
    }

    /**
     * 滑动时机计算
     */
    public interface ScrollCompat {
        boolean canChildScrollUp();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
