package com.hongri.recyclerview.badge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.NotificationCompat;

import com.hongri.recyclerview.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Create by zhongyao on 2021/5/27
 * Description:
 */

public class XiaoMiBadgeStrategy extends IBadgeStrategy {
    private static final String BADGE_TAG = "yao";
    private static final String BADGE_CHANNEL_ID = "yao_id";
    private static final String BADGE_LAST_COUNT = "yao_count";
    private static int notificationId = 1;

    /**
     * @param context
     * @param cls     传MainActivity.class
     * @param count   new Handler().postDelayed(new Runnable() {
     */
    @Override
    public void setBadgeCount(Context context, Class<?> cls, int count) {
//        setBadgeCountInternal(context, cls, count);
        new Handler().postDelayed(() -> setBadgeCountInternal(context, cls, count), 3000);
    }

    private void setBadgeCountInternal(Context context, Class<?> cls, int count) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        //发送新的通知之前,必须先把之前的通知给清除掉才能生效
        notificationManager.cancel(BADGE_TAG, notificationId - 1);
        if (count <= 0) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0之后添加角标需要NotificationChannel
            NotificationChannel channel = new NotificationChannel("badge3", "badge3", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context, "badge3")
                .setContentTitle("未读消息")
                .setContentText("您有" + count + "条未读消息")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setChannelId("badge3")
                .setNumber(count)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
        // 小米
        setBadgeInternal(count, notification);
        //暂设置不弹出，后续可根据需要调整
        notificationManager.notify(BADGE_TAG, notificationId++, notification);
    }

    private void setBadgeInternal(int count, Notification notification) {
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
