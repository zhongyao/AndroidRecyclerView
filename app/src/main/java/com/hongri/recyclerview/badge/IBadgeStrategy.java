package com.hongri.recyclerview.badge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public abstract class IBadgeStrategy {
    public void setBadgeCount(Context context, Class<?> cls, int count) {
    }

    protected String getLauncherClassName(Context context) {
        ComponentName launchComponent = getLauncherComponentName(context);
        if (launchComponent == null) {
            return "";
        } else {
            return launchComponent.getClassName();
        }
    }

    protected ComponentName getLauncherComponentName(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context
                .getPackageName());
        if (launchIntent != null) {
            return launchIntent.getComponent();
        } else {
            return null;
        }
    }
}
