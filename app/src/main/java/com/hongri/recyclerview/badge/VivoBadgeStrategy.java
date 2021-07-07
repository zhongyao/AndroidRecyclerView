package com.hongri.recyclerview.badge;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class VivoBadgeStrategy extends IBadgeStrategy {
    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        super.setBadgeCount(context, cls, count);

        try {
            String launcherClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launcherClassName)) {
                return;
            }
            Intent intent = new Intent();
            intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("className", launcherClassName);
            intent.putExtra("notificationNum", count);
            //FLAG_RECEIVER_INCLUDE_BACKGROUND
            intent.addFlags(0x01000000);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
