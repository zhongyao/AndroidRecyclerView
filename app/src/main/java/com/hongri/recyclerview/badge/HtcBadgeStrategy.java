package com.hongri.recyclerview.badge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class HtcBadgeStrategy extends IBadgeStrategy {
    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        try {
            ComponentName launcherComponentName = getLauncherComponentName(context);
            if (launcherComponentName == null) {
                return;
            }

            Intent intent1 = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
            intent1.putExtra("com.htc.launcher.extra.COMPONENT", launcherComponentName.flattenToShortString());
            intent1.putExtra("com.htc.launcher.extra.COUNT", count);
            context.sendBroadcast(intent1);

            Intent intent2 = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
            intent2.putExtra("packagename", launcherComponentName.getPackageName());
            intent2.putExtra("count", count);
            context.sendBroadcast(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
