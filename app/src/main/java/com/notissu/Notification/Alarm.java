package com.notissu.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.notissu.Activity.MainActivity;
import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class Alarm {
    Context context;

    public Alarm(Context con)
    {
        context = con;
    }

    public void popString(String string)
    {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification.Builder mBuilder = new android.app.Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ssu_mark);
        mBuilder.setTicker("Alarm.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("NOTISSU 공지사항 업데이트");
        mBuilder.setContentText(string + " 관련 공지사항");
        mBuilder.setDefaults(android.app.Notification.DEFAULT_SOUND | android.app.Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        nm.notify(111, mBuilder.build());
    }
}
