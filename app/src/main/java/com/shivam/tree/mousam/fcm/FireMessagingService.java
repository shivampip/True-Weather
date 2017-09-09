package com.shivam.tree.mousam.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shivam.tree.mousam.bgprogress.C;
import com.shivam.tree.mousam.bgprogress.MyNoti;

import java.net.URLDecoder;
import java.util.Map;

public class FireMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage msg) {
        super.onMessageReceived(msg);

        C.d("From: " + msg.getFrom());
        C.d("Notification Message Body: " + msg.getNotification().getBody());
        C.d("Msg Type:- "+msg.getMessageType());

        Log.d("OOMM", "Message Received:- "+msg.getNotification().getBody());

        ProcessFCM processFCM= new ProcessFCM(this, msg);


    }//onMsgReceivedEND


    public void subscribe(String topic){
        FirebaseMessaging messaging= FirebaseMessaging.getInstance();
        messaging.subscribeToTopic(topic);
    }


}//classEND
