package com.hongri.recyclerview.https;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.hongri.recyclerview.cache.BitmapCache;
import com.hongri.recyclerview.cache.BitmapDiskCache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @author：zhongyao on 2016/7/20 16:56
 * @description:
 */
public class HttpRequestManager implements IHttpRequest {

    private static final int SUCCESS = 0;
    private static final int FAIL = 1;
    private BitmapCache bitmapCache;
    private AsyncTask<Object, Integer, Object> task;

    /**
     * 执行状态
     */
    private int state = FAIL;

    private String method = METHOD_GET;

    private String uri;

    private Object resultData;

    private Context context;
    public HttpRequestManager() {
    }
    public HttpRequestManager(Context context) {
        this.context = context;
    }

    /**
     * 下载给出Uri的数据
     *
     * @param uri    url
     * @param method http请求方法 get or post
     * @return 获取的结果
     * @throws NullPointerException
     */
    private Object downloadUri(String uri, String method)
            throws NullPointerException {
        InputStream is;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.connect();// Starts the query
            int response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                /**
                 * 图片获取成功之后，将bitmap存储到缓存中
                 */
            BitmapCache.getInstance().putBitmap(uri,bitmap);
                /**
                 * 将图片存储到sd卡中
                 */
            BitmapDiskCache.getInstance(context).putBitmapToCache(uri);

//                bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data, 100, 100);
                state = SUCCESS;

            } else {
                state = FAIL;
            }
        } catch (Exception e) {
        }
        return bitmap;
    }

    @Override
    public void request(HttpIntent i, final IHttpRequest.IHttpRequestCallBack callBack) {
        uri = i.getStringExtra(HttpIntent.URI);
        method = i.getStringExtra(HttpIntent.METHOD);
        task = new AsyncTask<Object, Integer, Object>() {

            @Override
            protected Object doInBackground(Object... params) {
                return downloadUri(uri, method);
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                resultData = result;
                switch (state) {
                    case SUCCESS:
                        if (callBack != null)
                            callBack.onSuccess(HttpRequestManager.this);
                        break;
                    default:
                        if (callBack != null) {
                            callBack.onFailed("failure");
                        }
                        break;
                }
            }
        };
        task.execute();
    }

    @Override
    public Object getDataObject() {
        return resultData !=null ? resultData:"";
    }
}
