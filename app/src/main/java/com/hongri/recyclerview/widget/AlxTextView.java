package com.hongri.recyclerview.widget;

/**
 * Create by zhongyao on 2021/11/22
 * Description:
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AlxTextView extends View {
    TextPaint textPaint = null;
    StaticLayout staticLayout = null;
    int lineCount = 0;
    int width = 720;
    int height = 0;
    String txt = null;

    public AlxTextView(Context context, String content, int width, float textSize) {
        super(context);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        txt = content;
        this.width = width;
        staticLayout = new StaticLayout(txt, textPaint, width, Alignment.ALIGN_NORMAL, 1, 0, false);
        height = staticLayout.getHeight();
    }

    public int getLayoutHeight() {
        return height;
    }

    public int getLineCount() {
        return staticLayout.getLineCount();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        staticLayout.draw(canvas);
        super.onDraw(canvas);
    }

    /**
     * 在不绘制textView的情况下算出textView的高度，并且根据最大行数得到应该显示最后一个字符的下标，
     * 请在主线程顺序执行，第一个返回值是最后一个字符的下标，第二个返回值是高度
     * * @param textView
     * * @param content
     * * @param width
     * * @param maxLine
     * * @return
     */
    public static int[] measureTextViewHeight(TextView textView, String content, int width, int maxLine) {
        Log.i("Alex", "宽度是" + width);
        TextPaint textPaint = textView.getPaint();
        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Alignment.ALIGN_NORMAL, 1, 0, false);
        int[] result = new int[2];
        if (staticLayout.getLineCount() > maxLine) {//如果行数超出限制
            int lastIndex = staticLayout.getLineStart(maxLine) - 1;
            result[0] = lastIndex;
            result[1] = new StaticLayout(content.substring(0, lastIndex), textPaint, width, Alignment.ALIGN_NORMAL, 1, 0, false).getHeight();
            return result;
        } else {//如果行数没有超出限制
            result[0] = -1;
            result[1] = staticLayout.getHeight();
            return result;
        }
    }

    public static float getTextSize(TextView tv, String text) {
        Paint paint = new Paint();
        paint.setTextSize(tv.getTextSize());
        return paint.measureText(text);
    }
}
