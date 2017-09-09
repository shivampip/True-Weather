package com.shivam.tree.mousam.bgprogress;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.ShowWeather;

/**
 * Created by Sittu Agrawal on 16-01-2017.
 */

public class MyNoti {

    Context context;
    String title;
    String body;
    int icon;
    Intent intent;
    int requestCode;
    Uri voiceUri;

    public MyNoti(Context context) {
        this.context = context;
        title = "True Weather";
        body = "Notification from Shivam";
        icon = R.drawable.main_icon;
        requestCode = (int) System.currentTimeMillis();
        intent = new Intent(context, ShowWeather.class);
        voiceUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public MyNoti setTitle(String title) {
        this.title = title;
        return this;
    }

    public MyNoti setBody(String body) {
        this.body = body;
        return this;
    }

    public MyNoti setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public MyNoti setIntent(Intent intent) {
        this.intent = intent;
        this.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }

    public void show() {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pi = PendingIntent.getActivity(context, requestCode, intent, 0);
        Notification noti = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setSound(voiceUri)
                .setContentIntent(pi)
                .getNotification();
        nm.notify(requestCode, noti);
    }


}//classEND
