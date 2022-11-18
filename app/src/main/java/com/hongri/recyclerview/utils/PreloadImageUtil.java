package com.hongri.recyclerview.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * @author：hongri
 * @date：11/17/22
 * @description：Glide预加载图片工具类
 */
public class PreloadImageUtil {

    private static final String TAG = "PreloadImageUtil";

    public static final String IMAGE_URL = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp15.qhimg.com%2Fbdr%2F__%2Fd%2F_open360%2Fguniang125%2F64.jpg&refer=http%3A%2F%2Fp15.qhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671275184&t=cbed7145144c73b912428fe66031fdf0";

    /**
     * 只预加载图片
     * @param context
     * @param imgUrl
     */
    public static void preloadImage(Context context, String imgUrl) {
        Glide.with(context).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.DATA).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d(TAG, "onLoadFailed--e:" + (e != null ? e.getMessage() : "") + " isFirstResource:" + isFirstResource);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(TAG, "onResourceReady--isFirstResource:" + isFirstResource);
                return false;
            }
        }).preload();
    }

    /**
     * 预加载图片 + 展示
     * @param context
     * @param imgUrl
     */
    public static void preloadImageAndShow(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.DATA).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d(TAG, "onLoadFailed--e:" + (e != null ? e.getMessage() : "") + " isFirstResource:" + isFirstResource);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(TAG, "onResourceReady--isFirstResource:" + isFirstResource);
                return false;
            }
        }).into(imageView);
    }

}
