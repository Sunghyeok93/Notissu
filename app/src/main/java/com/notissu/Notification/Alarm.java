package com.notissu.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.notissu.Activity.MainActivity;
import com.notissu.R;
import com.notissu.Util.ResString;

import java.util.ArrayList;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class Alarm {

    public static void cancel(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    public static void showAlarm(Context context, ArrayList<String> notice) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        StringBuilder sb = new StringBuilder();
        if (notice.size() > 0) {
            sb.append("[");
            sb.append(notice.get(0));
            for (int i = 1; i < notice.size(); i++) {
                sb.append(", "+notice.get(i));
            }
            sb.append("]");
        }

        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ssu_mark);
        mBuilder.setTicker(ResString.getInstance().getString(ResString.RES_ALARM_TICKER));
        mBuilder.setContentTitle(ResString.getInstance().getString(ResString.RES_ALARM_TITLE));
        mBuilder.setContentText(sb.toString() + " 관련 공지사항");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        nm.notify(111, mBuilder.build());
    }
}
