package com.shivam.tree.mousam.bgprogress;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shivam.tree.mousam.Setting;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sittu Agrawal on 14-01-2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        this.context = context;

        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            if (mobile.isConnected() || wifi.isConnected()) {
                if (Setting.isNotiEnabled(context)) {
                    if (!isShown()) {
                        //setShown(context);
                        context.startService(new Intent(context, FetchService.class));
                    }
                } else {
                    //Notification Disabled
                }

            }
        }


    }//onReceiveEND


    final static String NOTI_SHOWN_TIME_HOD = "myNotiShownAt";
    final static String NOTI_SHOWN_TIME_DOM = "notiShownAt";

    public static void setShown(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hod = cal.get(Calendar.HOUR_OF_DAY);
        int dom = cal.get(Calendar.DAY_OF_MONTH);
        int modHod = hod - (hod % 3);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(NOTI_SHOWN_TIME_HOD, modHod);
        editor.putInt(NOTI_SHOWN_TIME_DOM, dom);
        editor.commit();
        Log.d("SHIVNOTI", "setShown hod= "+modHod);
    }

    public boolean isShown() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hod = cal.get(Calendar.HOUR_OF_DAY);
        int dom = cal.get(Calendar.DAY_OF_MONTH);
        int value = hod - (hod % 3);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int pValue = sp.getInt(NOTI_SHOWN_TIME_HOD, -1);
        int pDom = sp.getInt(NOTI_SHOWN_TIME_DOM, -1);
        Log.d("SHIVNOTI", "hod= "+value+" pHod="+pValue);
        if (dom != pDom) {
            return false;
        }
        if (value == pValue) {
            return true;
        } else {
            return false;
        }
    }

}//classEND
