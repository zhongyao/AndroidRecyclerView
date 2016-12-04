package com.hongri.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.hongri.recyclerview.fragment.DetailViewFragment;
import com.hongri.recyclerview.utils.Logger;

/**
 * @author：zhongyao on 2016/11/17 18:22
 * @description:View的工作原理
 */

public class ViewPrinciple extends View {

    int measureHeight;
    int measureWidth;
    private final int WRAP_WIDTH = 200;//像素
    private final int WRAP_HEIGHT = 200;
    private Context context;
    public ViewPrinciple(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 只处理AT_MOST情况也就是wrap_content,其他情况则沿用系统的测量值即可。
     *
     * 当View采用固定宽/高的时候，不管父容器的MeasureSpec是什么，View的MeasureSpec都是精确的模式
     * 并且其大小遵循LayoutParams中的大小。
     *
     * 当View的宽/高是match_parent时，如果父容器的模式是精准模式，那么View也是精准模式并且其大小是
     * 父容器的剩余空间
     *
     * 如果父容器是最大模式，那么View也是最大模式并且其大小不会超过父容器的剩余空间。
     *
     * 当View的宽/高是wrap_content时，不管父容器的模式是精准还是最大化，View的模式总是最大化
     * 并且大小不能超过父容器的剩余空间。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d("---------------------onMeasure()");
//        Logger.d("widthMeasureSpec:"+widthMeasureSpec+"heightMeasureSpec:"+heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height  = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();

        Logger.d("\nwidthMode:" + widthMode + "\nheightMode:"+heightMode);

        Logger.d("\nBEFORE---measureHeight:"+measureHeight+"  measureWidth:"+measureWidth);

//        if (widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED){
//
//        }
//        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY){
//
//        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(WRAP_WIDTH,WRAP_HEIGHT);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(WRAP_WIDTH,height);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width,WRAP_HEIGHT);
        }

        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();

        Logger.d("\n\nAFTER---measureHeight:"+measureHeight+"  measureWidth:"+measureWidth);

//        MeasureSpec.makeMeasureSpec(100, MeasureSpec.AT_MOST);
//        MeasureSpec.getMode();
//        MeasureSpec.getSize();
//        MeasureSpec.toString()
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Logger.d("------------------------layout");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d("------------------------onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("----------------------onDraw()");
    }

    /**
     * 当Activity/Fragment启动的时候，获取某个view的宽/高
     * （方法1）
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Logger.d("ViewPrinciple--onWindowFocusChanged");
        if (hasWindowFocus){
            int width = DetailViewFragment.newInstance().viewPrinciple.getMeasuredWidth();
            int height = DetailViewFragment.newInstance().viewPrinciple.getMeasuredHeight();
            Logger.d("method1--onWindowFocusChanged--width:"+width+" height:"+height);
        }
    }
}
