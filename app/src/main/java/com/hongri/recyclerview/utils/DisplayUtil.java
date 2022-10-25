package com.hongri.recyclerview.utils;
import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.11.12
 *
 */
public class DisplayUtil{
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 设置 {} 内字体高亮
     * @param context
     * @param text
     * @param normalColorResId 普通字体颜色
     * @param highlightColorResId 高亮字体颜色
     * @return
     */
    public static Spannable getFinalTextSpan(Context context, String text, int normalColorResId, int highlightColorResId, boolean setTextSize) {
        Spannable textSpan = null;
        try {
            if (context == null) {
                return null;
            }
            int startPos = text.indexOf("{");
            int endPos = text.indexOf("}");

            // 添加保护：对后端返回的子标题添加崩溃保护
            text = text.replace("{", "").replace("}", "");
            textSpan = new SpannableStringBuilder(text);
            endPos = endPos - 1;
            if (startPos != -1 && endPos != -1) {
                textSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(normalColorResId)),
                        0, startPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                if (setTextSize) {
                    textSpan.setSpan(new AbsoluteSizeSpan(10, true),
                            0, startPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    textSpan.setSpan(new AbsoluteSizeSpan(14, true),
                            startPos, endPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    textSpan.setSpan(new AbsoluteSizeSpan(10, true),
                            endPos, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }

                textSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(highlightColorResId)),
                        startPos, endPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                textSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(normalColorResId)),
                        endPos, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return textSpan;
    }
}