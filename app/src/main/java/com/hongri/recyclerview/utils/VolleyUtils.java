package com.hongri.recyclerview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.hongri.recyclerview.R;
import com.hongri.recyclerview.cache.BitmapCache;

/**
 * @author：zhongyao on 2016/7/13 13:56
 * @description:
 */
public class VolleyUtils {
    /**
     * @param context       上下文
     * @param imageUrl      图片URL
     * @param iv            imageview
     *                      允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，
     *                      则会对图片进行压缩，
     *                      指定成0的话就表示不管图片有多大，都不会进行压缩
     * @param width
     * @param height
     * @param default_image 默认图片
     */
    public static void VolleyImageRequest(Context context, String imageUrl, final ImageView iv, int width, int height, int default_image) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                }, width, height, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iv.setImageResource(R.mipmap.ic_launcher);
            }
        });
        mQueue.add(imageRequest);
    }

    /**
     * VolleyImageLoader:
     * ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，
     * 还可以过滤掉重复的链接，避免重复发送请求
     * @param context
     * @param imageUrl
     * @param iv
     * @param width
     * @param height
     * @param default_image
     * @param error_image
     */
    public static void VolleyImageLoader(Context context, String imageUrl, ImageView iv, int width, int height, int default_image, int error_image) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, default_image, error_image);
        imageLoader.get(imageUrl, listener, width, height);
    }

    /**
     * 相比VolleyImageLoader添加了缓存Cache
     * @param context
     * @param imageUrl
     * @param iv
     * @param width
     * @param height
     * @param default_image
     * @param error_image
     */
    public static void VolleyImageLoaderAndCache(Context context, String imageUrl, ImageView iv, int width, int height, int default_image, int error_image) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, default_image, error_image);
        imageLoader.get(imageUrl, listener, width, height);
    }

    /**
     * 使用NetworkImageView控件实现的网络图片加载（目前该方案没有出现图片重复加载问题）
     * @param context
     * @param imageUrl
     * @param iv
     * @param default_image
     * @param error_image
     */
    public static void VolleyNetworkImageView(Context context, String imageUrl, NetworkImageView iv, int default_image, int error_image){
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
        iv.setImageUrl(imageUrl,imageLoader);
        iv.setDefaultImageResId(default_image);
        iv.setErrorImageResId(error_image);
    }
}
