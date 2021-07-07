package com.hongri.recyclerview.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */
public class LenovoBadgeStrategy extends IBadgeStrategy {
    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
        try {
            Bundle extra = new Bundle();
            ArrayList<String> ids = new ArrayList<>();
            // 以列表形式传递快捷方式id，可以添加多个快捷方式id
//        ids.add("custom_id_1");
//        ids.add("custom_id_2");
            extra.putStringArrayList("app_shortcut_custom_id", ids);
            extra.putInt("app_badge_count", count);
            Uri contentUri = Uri.parse("content://com.android.badge/badge");
            Bundle bundle = context.getContentResolver().call(contentUri, "setAppBadgeCount", null, extra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
