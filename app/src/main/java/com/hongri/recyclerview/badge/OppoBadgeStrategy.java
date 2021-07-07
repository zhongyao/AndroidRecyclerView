package com.hongri.recyclerview.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 * OPPO角标适配（需先发邮件给OPPO官网申请）：https://open.oppomobile.com/bbs/forum.php?mod=viewthread&tid=2448&extra=page%3D1&aid=11392
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
