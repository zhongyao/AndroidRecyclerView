package com.hongri.recyclerview.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class HuaweiBadgeStrategy extends IBadgeStrategy {
    private static final String BADGE_TAG = "yao";
    private static final String BADGE_CHANNEL_ID = "yao_id";
    private static int notificationId = 1;

    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        try {
            String launchClassName = getLauncherClassName(context);
            if (TextUtils.isEmpty(launchClassName)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            bundle.putString("class", launchClassName);
            bundle.putInt("badgenumber", count);
            Uri uri = Uri.parse("content://com.huawei.android.launcher" + ".settings/badge/");
            context.getContentResolver().call(uri, "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
