package com.hongri.recyclerview.cache;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * @author：zhongyao on 2016/7/13 14:56
 * @description:内存缓存
 */
public class BitmapCache implements ImageLoader.ImageCache {
    public static BitmapCache bitmapCache;
    private LruCache<String, Bitmap> mCache;

    private BitmapCache() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
//                return bitmap.getRowBytes() * bitmap.getHeight();

                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static BitmapCache getInstance() {
        if (bitmapCache == null) {
            synchronized (BitmapCache.class) {
                if (bitmapCache == null) {
                    bitmapCache = new BitmapCache();
                }
            }
        }
        return bitmapCache;
    }

    /**
     * 从缓存中获取图片
     *
     * @param url
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    /**
     * 将图片存储到缓存
     *
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
