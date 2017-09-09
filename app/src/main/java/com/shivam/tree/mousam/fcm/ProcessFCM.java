package com.shivam.tree.mousam.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.shivam.tree.mousam.bgprogress.MyNoti;

import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by Sittu Agrawal on 17-01-2017.
 */

public class ProcessFCM {

    Context context;
    RemoteMessage msg;

    public final static String TYPE = "type";
    public final static String TYPE_NOTIFICATION = "noti";
    public final static String TYPE_WISHES = "wish";
    public final static String TYPE_DOWNLOAD = "custom";


    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String ICON_URL = "icon";

    String type = TYPE_NOTIFICATION;


    public ProcessFCM(Context context, RemoteMessage msg) {
        this.context = context;
        this.msg = msg;
        Map<String, String> map = msg.getData();
        if (map != null) {
            type = map.get(TYPE);
        }
        switch (type) {
            case TYPE_NOTIFICATION:
                sendNotification();
                break;
            case TYPE_WISHES:
                sendWishes();
                break;
            case TYPE_DOWNLOAD:
                sendDownload();
                break;
            default:
                sendNotification();
                break;
        }
    }//consEND


    public void sendNotification() {
        String img = msg.getNotification().getIcon();
        String title = msg.getNotification().getTitle();
        String body = msg.getNotification().getBody();
        String sound = msg.getNotification().getSound();

        try {
            MyNoti myNoti = new MyNoti(context);
            myNoti.setTitle("SN "+URLDecoder.decode(title, "UTF-8"));
            myNoti.setBody(URLDecoder.decode(body, "UTF-8"));
            myNoti.show();
        } catch (Exception e) {
        }
    }

    private void sendDownload() {

    }


    public void sendWishes() {
        Log.d("OOMM", "Inside sendWishes");
        Map<String, String> map = msg.getData();

        String title = map.get(TITLE);
        String desc = map.get(DESC);
        String iconUrl = map.get(ICON_URL);

        Log.d("OOMM", "Title:- "+title);
        Log.d("OOMM", "Desc:- "+desc);
        Log.d("OOMM", "IconUrl:- "+iconUrl);




        Log.d("OOMM", "Notification Showed");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TYPE, TYPE_WISHES);
        editor.putString(TITLE, title);
        editor.putString(DESC, desc);
        editor.putString(ICON_URL, iconUrl);
        editor.commit();
        Log.d("OOMM", "Data Writter in SP");



//        MyNoti myNoti = new MyNoti(context);
//        myNoti.setTitle("Data Written");
//        myNoti.setBody(desc);
//        myNoti.show();
    }

    public static void removeDialogData(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove(TYPE);
        editor.remove(TITLE);
        editor.remove(DESC);
        editor.remove(ICON_URL);
        editor.commit();
    }

    public static DialogData getDialogData(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String type= sp.getString(TYPE, null);
        String title= sp.getString(TITLE, null);
        String desc= sp.getString(DESC, null);
        String iconUrl= sp.getString(ICON_URL, null);
        DialogData dd= new DialogData(type, title, desc, iconUrl);
        return dd;
    }



    static class DialogData{
        String type;
        String title;
        String desc;
        String iconUrl;

        public DialogData(String type, String title, String desc, String iconUrl){
            this.type= type;
            this.title= title;
            this.desc= desc;
            this.iconUrl= iconUrl;
        }

        public String getType() {
            return type;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getDesc() {
            return desc;
        }

        public String getTitle() {
            return title;
        }
    }//innerClassEND


    //
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.shiv02d)
//                .setContentTitle("FCM Message")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }

}//classEND
