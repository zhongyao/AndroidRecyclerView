package com.hongri.recyclerview.badge;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class SamsungBadgeStrategy extends IBadgeStrategy {

    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        super.setBadgeCount(context, cls, count);

        try {
            String launcherClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launcherClassName)) {
                return;
            }
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", count);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
