package com.hongri.recyclerview.utils;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;


/**
 * Create by zhongyao on 2021/5/29
 * Description:
 */
public class DrawableUtil {
    /**
     * 创建背景颜色
     *
     * @param color       填充色
     * @param strokeColor 线条颜色
     * @param strokeWidth 线条宽度  单位px
     * @param radius      角度  px,长度为8,分别表示:
     *                    topLeftRadius_x,topLeftRadius_y,topRightRadius_x, topRightRadius_y,
     *                    bottomRightRadius_x,bottomRightRadius_y,bottomLeftRadius_x, bottomLeftRadius_y.
     */
    public static GradientDrawable createRectangleDrawable(@ColorInt int color, @ColorInt int strokeColor, int strokeWidth, float radius[]) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            radiusBg.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            radiusBg.setColor(color);
            //设置线条粗心和颜色,px
            radiusBg.setStroke(strokeWidth, strokeColor);
            //每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
            if (radius != null && radius.length == 8) {
                radiusBg.setCornerRadii(new float[]{radius[0], radius[1], radius[2], radius[3], radius[4], radius[5], radius[6], radius[7]});
            }
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
        }
    }
}
