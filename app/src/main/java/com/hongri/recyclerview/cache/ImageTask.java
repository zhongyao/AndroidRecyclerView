package com.hongri.recyclerview.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.hongri.recyclerview.utils.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @author：zhongyao on 2016/7/27 16:21
 * @description:
 * 图片缓存任务类ImageTask：
 * 与ImageWorker类似，不过ImageTask的SD卡缓存使用到了DiskLruCache
 */
public class ImageTask {
    private static ImageTask imageTask;
    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private Set<BitmapWorkTask> taskCollection;

    private ImageTask() {
        taskCollection = new HashSet<>();
    }

    public static ImageTask getInstance() {
        if (imageTask == null) {
            synchronized (ImageTask.class) {
                if (imageTask == null) {
                    imageTask = new ImageTask();
                }
            }
        }
        return imageTask;
    }

    public void loadImage(Context context, ImageView iv, String imageUrl) {
        Bitmap bitmap = BitmapCache.getInstance().getBitmap(imageUrl);
        if (bitmap == null) {
            BitmapWorkTask task = new BitmapWorkTask(context, iv, imageUrl);
            taskCollection.add(task);
            task.execute(imageUrl);
        } else {
            if (iv != null) {
                iv.setImageBitmap(bitmap);
            }
        }
    }

    class BitmapWorkTask extends AsyncTask<String, Void, Bitmap> {
        private Context context;
        private ImageView iv;
        private String imageUrl;

        public BitmapWorkTask(Context context, ImageView iv, String imageUrl) {
            this.context = context;
            this.iv = iv;
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap;
            bitmap = BitmapDiskCache.getInstance(context).getBitmapFromDiskCache(imageUrl);
            if (bitmap == null) {
                bitmap = BitmapDiskCache.getInstance(context).putBitmapToCache(imageUrl);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (iv != null && bitmap != null){
                iv.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }
}
