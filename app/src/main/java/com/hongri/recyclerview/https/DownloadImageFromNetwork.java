package com.hongri.recyclerview.https;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import androidx.collection.LruCache;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author：zhongyao on 2016/7/11 10:39
 * @description:从网络下载图片，并将下载好的图片存储到内存缓存、SD卡缓存中
 */
public class DownloadImageFromNetwork implements Runnable {
    private ImageView mImageView;
    private String imgUrl;
    private Handler mHandler;
    private LruCache<String, Bitmap> mLruCache;
    private String bitmapPath;

    public DownloadImageFromNetwork(ImageView mImageView, String imgUrl,
                                    Handler mHandler, LruCache<String, Bitmap> mLruCache, String bitmapPath) {
        this.mImageView = mImageView;
        this.imgUrl = imgUrl;
        this.mHandler = mHandler;
        this.mLruCache = mLruCache;
        this.bitmapPath = bitmapPath;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream;
            URL url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(10 * 1000);
            conn.setDoInput(true);
            conn.connect();// Starts the query
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                inputStream = conn.getInputStream();
                if (inputStream != null) {
                    final Bitmap bitmapNetwork = BitmapFactory.decodeStream(inputStream);
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bitmapNetwork);
                        }
                    });
                    /**
                     * 此时输入流inputStream不存在了（输入流使用一次后就没有了），
                     * 所以以下两行传送的代码是Bitmap对象
                     */
                    addBitmapToCache(imgUrl, bitmapNetwork);
                    addBitmapToDisk(imgUrl, bitmapNetwork);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBitmapToDisk(String imgUrl, Bitmap bitmapNetwork) {
        OutputStream outputStream = null;
        BufferedOutputStream bos = null;
        InputStream inputStream = null;
        File file = new File(bitmapPath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
        }
        try {
            String[] fileNames = imgUrl.split("/");
            String fileName = fileNames[fileNames.length - 1];
            outputStream = new FileOutputStream(bitmapPath + fileName);
            bos = new BufferedOutputStream(outputStream);
            /**
             * 以下三行的代码表示：
             * 将Bitmap对象转换为inputStream
             */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapNetwork.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            inputStream = new ByteArrayInputStream(baos.toByteArray());

            byte[] data = new byte[1024];
            while (inputStream.read(data) != -1) {
                bos.write(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addBitmapToCache(String imgUrl, Bitmap bitmapNetwork) {
        mLruCache.put(imgUrl, bitmapNetwork);
    }
}
