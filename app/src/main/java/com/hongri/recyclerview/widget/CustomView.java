package com.hongri.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.hongri.recyclerview.utils.Logger;

/**
 * @author：zhongyao on 2016/10/29 16:28
 * @description:View的事件体系
 */

public class CustomView extends View implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{
    int left, right, top, bottom;
    float x, y, translationX, translationY;

    float axisX;
    float axisY;//当前view左上角的x,y坐标
    float rawAxisX,rawAxisY;//相对于手机屏幕左上角的x,y坐标
    float touchSlop;//滑动的最小距离
    private VelocityTracker velocityTracker;
    private GestureDetector gestureDetector;

    private Context context;

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d("CustomViw-3args");

        this.context = context;
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d("CustomView-2args");

        this.context = context;
    }

    public CustomView(Context context) {
        super(context);
        Logger.d("CustomView-1args");

        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("onDraw");

        initView();

//        LogMessage();
    }

    private void initView() {
        gestureDetector = new GestureDetector(context,this);
        gestureDetector.setIsLongpressEnabled(false);

        //Scroller实现有过渡效果的滑动
        Scroller scroller = new Scroller(context);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean consume = gestureDetector.onTouchEvent(event);

//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Logger.d("ACTION_DOWN");
////                LogMessage();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                Logger.d("ACTION_MOVE");
//                velocityTracker = VelocityTracker.obtain();
//                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(1000);
//                int xVelocity = (int) velocityTracker.getXVelocity();
//                int yVelocity = (int) velocityTracker.getYVelocity();
//                LogMessage(xVelocity,yVelocity);
//
//                velocityTracker.clear();
//                velocityTracker.recycle();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                Logger.d("ACTION_UP");
////                LogMessage();
//                break;
//        }
        return consume;
//        return false;
    }

    private void LogMessage(int xVelocity, int yVelocity) {
        //速度= 划过的像素数/时间段
        Logger.d("xVelocity:"+xVelocity+"像素\byVelocity:"+yVelocity+"像素");
    }

    private void LogMessage() {
        left = getLeft();
        right = getRight();
        top = getTop();
        bottom = getBottom();

        translationX = getTranslationX();
        translationY = getTranslationY();

        x = left + translationX;
        y = top + translationY;

        Logger.d("left:" + left + "\nright:" + right + "\ntop:" + top + "\nbottom:"
                + bottom + "\ntranslationX:" + translationX + "\ntranslationY:"
                + translationY + "\nbeginX:" + x + "\ny:" + y);
        //---------------------------------------
        axisX = getX();
        axisY = getY();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        Logger.d("axisX:"+axisX+"\naxisY:"+axisY+"\ntouchSlop:"+touchSlop);


    }

    @Override
    public boolean onDown(MotionEvent e) {

        Logger.d("GestureDetector -->onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Logger.d("GestureDetector-->onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Logger.d("GestureDetector -->onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Logger.d("GestureDetector -->onScroll");
        return true;
    }


    /**
     * 此处onLongRress方法未触发，原因不明...
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Logger.d("GetsureDetector -->onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Logger.d("GestureDetector -->onFling");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Logger.d("GestureDetector-->onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Logger.d("GestureDetector-->onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Logger.d("GestureDetector-->onDoubleTapEvent");
        return true;
    }
}
