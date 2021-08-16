package com.hongri.recyclerview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Create by zhongyao on 2021/8/16
 * Description:
 */
public class MyBroadcast extends BroadcastReceiver {
    private static final String TAG = "MyBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive:");

        if (intent != null) {
            String name = intent.getStringExtra("name");
            int age = intent.getIntExtra("age", 20);

            Bundle extra = intent.getExtras();
            if (extra != null) {
                name = extra.getString("name");
                age = extra.getInt("age");
            }
            Log.d(TAG, "name:" + name + " age:" + age);
        }
    }
}
