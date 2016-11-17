package com.hongri.recyclerview;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author：zhongyao on 2016/7/29 14:12
 * @description:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 如果使用Universal-Image-Loader的时候出现OOM，可以进行如下设置：
         *
         1、减少线程池中线程的个数，在ImageLoaderConfiguration中的（.threadPoolSize）中配置，推荐配置1-5
         2、在DisplayImageOptions选项中配置bitmapConfig为Bitmap.Config.RGB_565，因为默认是ARGB_8888， 使用RGB_565会比使用ARGB_8888少消耗2倍的内存
         3、在ImageLoaderConfiguration中配置图片的内存缓存为memoryCache(new WeakMemoryCache()) 或者不使用内存缓存
         4、在DisplayImageOptions选项中设置.imageScaleType(ImageScaleType.IN_SAMPLE_INT)或者imageScaleType(ImageScaleType.EXACTLY)
         */

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
