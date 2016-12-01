package com.hongri.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
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

    public ViewPrinciple(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 只处理AT_MOST情况也就是wrap_content,其他情况则沿用系统的测量值即可。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Logger.d("widthMeasureSpec:"+widthMeasureSpec+"heightMeasureSpec:"+heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height  = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();

        Logger.d("\nwidthMode:" + widthMode + "\nheightMode:"+heightMode);

        Logger.d("\nBEFORE---measureHeight:"+measureHeight+"  measureWidth:"+measureWidth);

        if (widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED){

        }
        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY){

        }

//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(WRAP_WIDTH,WRAP_HEIGHT);
//        }else if (widthMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(WRAP_WIDTH,height);
//        }else if (heightMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(width,WRAP_HEIGHT);
//        }

        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();

        Logger.d("\n\nAFTER---measureHeight:"+measureHeight+"  measureWidth:"+measureWidth);

//        MeasureSpec.makeMeasureSpec(100, MeasureSpec.AT_MOST);
//        MeasureSpec.getMode();
//        MeasureSpec.getSize();
//        MeasureSpec.toString()
    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
