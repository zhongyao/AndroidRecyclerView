package com.hongri.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hongri.recyclerview.utils.DataUtil;
import com.hongri.recyclerview.utils.Logger;
import com.nineoldandroids.view.ViewHelper;

/**
 * @author：zhongyao on 2016/11/3 10:56
 * @description:滑动view测试
 *
scrollTo(x, y)：通过invalidate使view直接滚动到参数x和y所标定的坐标
scrollBy(x, y)：通过相对于当前坐标的滚动。

当scrollTo()的传入参数为负的时候，view就向坐标轴正方向滚动；
当为正的时候，view就向坐标轴负方向滚动。

 */

public class CustomScrollView extends View {

    private Context context;
    private int[] points;
    private int circle = 12;
    private int strokeWidth = 30;
    private Paint paint;
    private String RED = "#FFCC0000";
    int scrollX;
    int scrollX2;
    float beginX;
    float moveX;
    float deltaX = 10;
    float endX;
    int mLastX,mLastY;
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        paint = new Paint();
        paint.setColor(Color.parseColor(RED));
        points = DataUtil.points;

//        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0;i<points.length;i++){

            canvas.drawLine(0,500,2000,500,paint);
            canvas.drawCircle(Float.valueOf(points[i]),500,Float.valueOf(circle),paint);
        }
    }

    /**
     * 总结:
     * scrollTo()指的是移动到制定的(x,y)位置，
     * 而scrollBy(x,y)指的是，在当前位置在移动(x,y)个位置
     * @param event
     * @return
     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                beginX = event.getX();
//                Logger.d("\nbeginX:"+ beginX);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = event.getX() - beginX;
//                beginX = event.getX();//
//                if (moveX > 0){
//                    deltaX = -Math.abs(deltaX);
//                }else {
//                    deltaX = Math.abs(deltaX);
//                }
//                scrollBy((int) deltaX,0);
//                Logger.d("\nmoveX:"+ moveX+"\ndeltaX:"+deltaX);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                if (event.getX() > 0){
//                    endX = -event.getX();
//                }else {
//                    endX = Math.abs(event.getX());
//                }
//                scrollTo((int) endX,0);
//                Logger.d("\nendX:"+ endX);
//                invalidate();
//                break;
//        }
//        return true;
//
//    }


    /**
     * nineoldandroids的使用：
     * 移动整个View
     *
     * link:
     * http://nineoldandroids.com/
     * sources:
     * https://github.com/JakeWharton/NineOldAndroids
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                Logger.d("move,deltaX:"+deltaX + " deltaY:"+deltaY);
                int translationX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewHelper.getTranslationY(this) + deltaY);

                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
