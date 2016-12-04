package com.hongri.recyclerview.activity;

import android.os.Bundle;

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
        //Fabric统计初始化
//        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("红日");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.getInstance()).commit();
        }

    }
}
