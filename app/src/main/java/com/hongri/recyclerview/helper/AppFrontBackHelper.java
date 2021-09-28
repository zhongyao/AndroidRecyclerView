package com.hongri.recyclerview.helper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Create by hongri on 2021/9/27
 * Description: 应用前后台状态监听帮助类，仅在Application中使用
 */
public class AppFrontBackHelper {

    private final String TAG = "AppFrontBackHelper";

    private OnAppStatusListener mOnAppStatusListener;


    public AppFrontBackHelper() {

    }

    /**
     * 注册状态监听，仅在Application中使用
     *
     * @param application
     * @param listener
     */
    public void register(Application application, OnAppStatusListener listener) {
        Log.d(TAG, "register");
        mOnAppStatusListener = listener;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void unRegister(Application application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        //打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d(TAG, "onActivityCreated---> activity:" + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            Log.d(TAG, "onActivityStarted---> activity:" + activity + " activityStartCount:" + activityStartCount);
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1) {
                //从后台切到前台
                if (mOnAppStatusListener != null) {
                    mOnAppStatusListener.onFront();
                }
            }
        }


        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "onActivityResumed---> activity:" + activity);
            if (mOnAppStatusListener != null) {
                mOnAppStatusListener.onResume(activity);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused---> activity:" + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            Log.d(TAG, "onActivityStopped---> activity:" + activity + " activityStartCount:" + activityStartCount);
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0) {
                //从前台切到后台
                if (mOnAppStatusListener != null) {
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, "onActivitySaveInstanceState---> activity:" + activity);

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, "onActivityDestroyed---> activity:" + activity);
        }
    };

    public interface OnAppStatusListener {
        void onFront();

        void onBack();

        void onResume(Activity activity);
    }

}