package com.hongri.recyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;

//import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.hongri.recyclerview.R;
import com.hongri.recyclerview.fragment.HomeFragment;
import com.hongri.recyclerview.utils.Logger;

//import io.fabric.sdk.android.Fabric;
/**
 * @author：zhongyao on 2016/6/30 14:32
 * @description:主界面Activity
 */
public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("MainActivity--onCreate()");
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("红日");
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.getInstance()).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("MainActivity--onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d("MainActivity--onRestoreInstanceState");
    }
}
