package com.hongri.recyclerview.badge;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class SonyBadgeStrategy extends IBadgeStrategy {

    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (TextUtils.isEmpty(launcherClassName)) {
            return;
        }
        try {
            //官方给出方法
            ContentValues contentValues = new ContentValues();
            contentValues.put("badge_count", count);
            contentValues.put("package_name", context.getPackageName());
            contentValues.put("activity_name", launcherClassName);
            SonyAsyncQueryHandler asyncQueryHandler = new SonyAsyncQueryHandler(context
                    .getContentResolver());
            asyncQueryHandler.startInsert(0, null, Uri.parse("content://com.sonymobile.home" +
                    ".resourceprovider/badge"), contentValues);
        } catch (Exception e) {
            try {
                //网上大部分使用方法
                Intent intent = new Intent("com.sonyericsson.home.action.UPDATE_BADGE");
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", count > 0);
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
                        launcherClassName);
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String
                        .valueOf(count));
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context
                        .getPackageName());
                context.sendBroadcast(intent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private static class SonyAsyncQueryHandler extends AsyncQueryHandler {
        SonyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }
    }
}
