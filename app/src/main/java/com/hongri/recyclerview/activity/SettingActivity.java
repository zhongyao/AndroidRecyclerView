package com.hongri.recyclerview.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import android.util.Log;

import com.hongri.recyclerview.R;
import com.hongri.recyclerview.fragment.OpenFragment;
import com.hongri.recyclerview.fragment.SettingFragment;

/**
 * @author：zhongyao on 2016/8/3 17:49
 * @description:
 */
public class SettingActivity extends BaseActivity implements SettingFragment.OpenCallback {
    private static final String TAG = "SettingActivity";
    /**
     * adb指令启动Activity并传参 测试开关：
     * adb shell am start -n com.hongri.recyclerview/com.hongri.recyclerview.activity.SettingActivity --es name zhongyao --ei age 18
     *
     */
    private static boolean testForADB = false;

    public static boolean isSettingActivity = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Log.d(TAG, "onCreate");
        isSettingActivity = true;

        if (testForADB) {
            Intent intent = getIntent();
            if (intent == null) {
                Log.d(TAG, "intent is null");
                return;
            }
            Bundle extras = intent.getExtras();
            if (extras == null) {
                Log.d(TAG, "extra is null");
                return;
            }
            Uri uri = intent.getData();
            String extraName = extras.getString("name");
            int extraAge = extras.getInt("age");
            String name = intent.getStringExtra("name");
            int age = intent.getIntExtra("age", -1);

            Log.d(TAG, "uri:" + uri + " extraName:" + extraName + " extraAge:" + extraAge + " name:" + name + " age:" + age);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container, SettingFragment.getInstance());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        SettingFragment.setOpenCallback(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        isSettingActivity = false;
    }

    @Override
    public void openNewFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, OpenFragment.getInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
