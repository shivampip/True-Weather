package com.shivam.tree.mousam.backup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.bgprogress.NetworkChangeReceiver;
import com.shivam.tree.mousam.shiv.WeahterDay;
import com.shivam.tree.mousam.shiv.WeatherItem;

import java.util.Date;

/**
 * Created by Sittu Agrawal on 10-01-2017.
 */

public class Backup {

    Context context;
    SharedPreferences sp;

    public Backup(Context context) {
        this.context = context;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    final public static String BACKUP_NAME = "thanksGoogle";
    final public static String IS_BACKUPE = "isBackup";
    final public static String BACKUP_TIME = "backupTime";
    final public static String BACKUP_EXP = "backupExpairy";
    final public static String CITY = "city";
    final public static String FORMAT_ADD = "formatedAdd";
    final private static int BACKUP_EXP_DURATION = 10; //Minutes

    public void backup(BackupData backupData) {
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(backupData);
        editor.putString(BACKUP_NAME, json);
        editor.putBoolean(IS_BACKUPE, true);
        Date dt = new Date();
        editor.putLong(BACKUP_TIME, dt.getTime());
        editor.putLong(BACKUP_EXP, dt.getTime() + BACKUP_EXP_DURATION * 60 * 1000);
        editor.commit();
        NetworkChangeReceiver.setShown(context);
        Log.d(Const.TAG, "BACKUP SAVED");
    }


    public BackupData restore() {
        Gson gson = new Gson();
        String json = sp.getString(BACKUP_NAME, "Not FounD");
        BackupData backupData = gson.fromJson(json, BackupData.class);
        Log.d(Const.TAG, "BACKUP RESTORED");
        return backupData;
    }

    public boolean isBackupAvailable() {
        return sp.getBoolean(IS_BACKUPE, false);
    }

    public long getBackupTime() {
        return sp.getLong(BACKUP_TIME, 0);
    }

    public String getBackupDuration() {
        if(getBackupTime()==0){
            return "Now";
        }
        Date dt = new Date();
        long t = dt.getTime() - getBackupTime();
        int sec= (int) (t/1000);
        if(sec>60){
            return ((int)sec/60)+ " min";
        }else {
            return sec+" sec";
        }
    }

    public long getBackupExp() {
        return sp.getLong(BACKUP_EXP, 0);
    }

    public boolean isBackupFresh(){
        if(!isBackupAvailable()){
            return false;
        }
        Date dt= new Date();
        long t= dt.getTime()- getBackupTime();
        int sec= (int) t/1000;
        if(sec<BACKUP_EXP_DURATION*60&&sec>0){
            return true;
        }else {
            return false;
        }
    }

    public void removeBackup() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_BACKUPE, false);
        editor.commit();
    }

    public void backupOne(String para, String city) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(para, city);
        editor.commit();
    }

    public String restoreOne(String para) {
        return sp.getString(para, null);
    }

    public boolean isBackupAvailOne(String para) {
        String value = sp.getString(para, null);
        return value != null;
    }


}//classEND
