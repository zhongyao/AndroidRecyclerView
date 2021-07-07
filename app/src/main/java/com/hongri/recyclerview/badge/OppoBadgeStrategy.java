package com.hongri.recyclerview.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class OppoBadgeStrategy extends IBadgeStrategy {
    private int mCurrentAppBadgeCount = -1;

    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        if (mCurrentAppBadgeCount == count) {
            return;
        }

        mCurrentAppBadgeCount = count;

        try {
            Bundle extras = new Bundle();
            extras.putInt("app_badge_count", count);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
