package com.hongri.recyclerview.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import com.hongri.recyclerview.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author：zhongyao on 2016/7/29 11:14
 * @description:
 */
public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("WelcomeActivity--onCreate()---> isTaskRoot:" + this.isTaskRoot());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }

        Uri uri = getIntent().getData();
        if (uri != null) {
            //完成的url信息
            String url = uri.toString();
            Log.d(TAG, "url:" + url);
            //scheme部分
            String scheme = uri.getScheme();
            Log.d(TAG, "scheme:" + scheme);
            //host部分
            String host = uri.getHost();
            Log.d(TAG, "host:" + host);
            //port部分
            int port = uri.getPort();
            Log.d(TAG, "port:" + port);
            //访问路径
            String path = uri.getPath();
            Log.d(TAG, "path:" + path);
            //Query部分
            String query = uri.getQuery();
            Log.d(TAG, "query:" + query);
            //获取所有参数
            HashMap<String, Object> hashMap = new HashMap<>();
            Set<String> queryKeys = uri.getQueryParameterNames();
            Log.d(TAG, "queryKeys:" + queryKeys.toString());

            for (String queryKey : queryKeys) {
                List<String> queryValues = uri.getQueryParameters(queryKey);
                for (String queryValue : queryValues) {
                    hashMap.put(queryKey, queryValue);
                }
            }
            Log.d(TAG, "param:" + hashMap.toString());
        } else {
            Log.d(TAG, "uri is null");
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState--onCreate()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState--onCreate()");
    }
}
