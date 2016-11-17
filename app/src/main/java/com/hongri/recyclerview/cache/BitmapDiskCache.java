package com.hongri.recyclerview.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.hongri.recyclerview.utils.APPUtils;
import com.hongri.recyclerview.utils.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author：zhongyao on 2016/7/23 11:22
 * @description:SD卡缓存
 */
public class BitmapDiskCache {
    public static BitmapDiskCache bitmapDiskCache;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "zhongyao";
    private DiskLruCache mDiskLruCache;
    private String cachePath;
    private File cacheDir;
    private Context context;
    private BitmapDiskCache(Context context) {
        try {
            this.context = context;
            cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, APPUtils.getAPPVersion(context), 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private File getDiskCacheDir(Context context, String uniqueName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static BitmapDiskCache getInstance(Context context) {
        if (bitmapDiskCache == null) {
            synchronized (BitmapDiskCache.class) {
                if (bitmapDiskCache == null) {
                    try {
                        bitmapDiskCache = new BitmapDiskCache(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bitmapDiskCache;
    }

    /**
     * 从SD卡获取
     *
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmapFromDiskCache(String imageUrl) {
        Logger.d("getBitmapFromDiskCache77");
        Bitmap bitmap = null;
        if (mDiskLruCache != null) {
            Logger.d("getBitmapFromDiskCache80");

            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot;
            try {
                snapShot = mDiskLruCache.get(key);
                if (snapShot != null) {
                    InputStream is = snapShot.getInputStream(0);
                    bitmap = BitmapFactory.decodeStream(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 存入SD卡中(成功后，顺便存入内存缓存中O(∩_∩)O哈哈~)
     *
     * @param imageUrl
     */
    public Bitmap putBitmapToCache(final String imageUrl) {
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapShot = null;
        Bitmap bitmap = null;
        try {
            // 生成图片URL对应的key
            String key = hashKeyForDisk(imageUrl);
            // 查找key对应的缓存
            snapShot = mDiskLruCache.get(key);
            if (snapShot == null) {
                // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (downloadUrlToStream(imageUrl, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
                // 缓存被写入后，再次查找key对应的缓存
                snapShot = mDiskLruCache.get(key);
            }
            if (snapShot != null) {
                fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }

            // 将缓存数据解析成Bitmap对象
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
            if (bitmap != null) {
                // 将Bitmap对象添加到内存缓存当中
                BitmapCache.getInstance().putBitmap(imageUrl, bitmap);
            }
            return bitmap;

//            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor == null && fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 将字符串进行MD5编码,以完全符合文件的命名规则
     *
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 下载图片
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 清除Disk缓存
     */
    public void clearBitmapDiskCache() {
        try {
//            if (cacheDir != null && cacheDir.exists()){
//                cacheDir.delete();
//            }
            if (mDiskLruCache != null){
                mDiskLruCache.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
