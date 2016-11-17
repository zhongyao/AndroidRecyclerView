package com.hongri.recyclerview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.hongri.recyclerview.cache.BitmapUtil;

import java.lang.ref.WeakReference;

/**
 * @author：zhongyao on 2016/7/11 14:30
 * @description：图片异步加载类
 */
public class AsyncUtil {
    private static final int LOCALIMAGE = 0;
    private static final int NETWORKIMAGE = 1;

    public static void loadBitmap(Context context, int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(context, imageView);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId,LOCALIMAGE);
        }
    }

    public static void loadBitmap(Context context,String url,ImageView imageView){
        final BitmapWorkerTask task = new BitmapWorkerTask(context,imageView);
        final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(),task);
        imageView.setImageDrawable(asyncDrawable);
        task.execute(url,NETWORKIMAGE);
    }

    private static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res);
            bitmapWorkerTaskReference =
                    new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }


    private static class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        public int data = 0;
        private Context context;

        public BitmapWorkerTask(Context context, ImageView imageView) {
            this.context = context;
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            data = (int) params[0];
            //将一张图片压缩成100*100的缩略图
            return BitmapUtil.decodeSampledBitmapFromResource(context.getResources(), data, 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
