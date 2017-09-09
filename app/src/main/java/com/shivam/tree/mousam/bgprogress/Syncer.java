package com.shivam.tree.mousam.bgprogress;

import android.content.Context;

import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.backup.Backup;
import com.shivam.tree.mousam.backup.BackupData;
import com.shivam.tree.mousam.shiv.ShivOWAS;
import com.shivam.tree.mousam.shiv.ShivWeather;
import com.shivam.tree.mousam.shiv.ShivWeatherListener;
import com.shivam.tree.mousam.shiv.WeatherForcastListener;
import com.shivam.tree.mousam.shiv.WeatherItem;

/**
 * Created by Sittu Agrawal on 16-01-2017.
 */

public class Syncer {

    Context context;
    ResponseCallback rcb;

    ShivOWAS shivOWAS;
    Backup backup;
    BackupData backupData;

    public Syncer(Context context, ResponseCallback rcb) {
        this.context = context;
        this.rcb = rcb;
        init();
    }

    public void init() {
        backup = new Backup(context);
        backupData = new BackupData();
        syncData();
    }


    public void syncData() {
        if (backup.isBackupFresh()) {
            if (backup.isBackupAvailable()) {
                rcb.sendMsg("last synced " + backup.getBackupDuration() + " ago");
            }
        } else if (Const.isNetworkAvailable(context)) {
            rcb.sendMsg("Syncing...");
            if (backup.isBackupAvailOne(Backup.CITY)) {
                String city = backup.restoreOne(Backup.CITY);
                String formatAdd = backup.restoreOne(Backup.FORMAT_ADD);
                rcb.sendMsg("City is " + city);
                rcb.sendMsg("FormatAdd is " + formatAdd);
                getJankari(city, formatAdd);
                getForcastJankari(city, formatAdd);
            } else {
                rcb.onFailed("No City Selected");
            }
        } else {
            rcb.onFailed("Network not available");
        }
    }//syncDataEND


    public void getJankari(String city, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getApiKey(), ShivOWAS.RES_CURRENT_WEATHER);
        shivOWAS.setCity(city);
        getJankari(formatAdd);
    }

    //Getting Current Weather Info
    public void getJankari(final String formatAdd) {
        shivOWAS.setCurrentWeatherListener(new ShivWeatherListener<ShivWeather>() {
            @Override
            public void onResponse(ShivWeather myWea) {
                backup(myWea, formatAdd);
            }

            @Override
            public void onFailure(String err) {
                rcb.sendMsg("Error in getJankari:- "+ err);
            }
        });
    }

    private void backup(ShivWeather shivWeather, String formatAdd) {
        backupData.setShivWeather(shivWeather);
        backupData.setFormatAdd(formatAdd);
        rcb.sendMsg("ShivWeather Backup Success");
    }

    private void backup(WeatherItem[] items) {
        backupData.setWeatherItems(items);
        backup.backup(backupData);
        rcb.sendMsg("Forcast Backup Success");
        rcb.sendMsg("Full Backup Completed");
        rcb.onCompleted("Success");
    }


    public void getForcastJankari(String city, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getApiKey(), ShivOWAS.RES_FORCASE);
        shivOWAS.setCity(city);
        getForcastJankari(formatAdd);
    }

    public void getForcastJankari(final String formatAdd) {
        shivOWAS.setWeatherForcastListener(new WeatherForcastListener() {
            @Override
            public void onResponse(WeatherItem[] items) {
                backup(items);
            }

            @Override
            public void onFailure(String err) {
            }
        });
    }

}//classENd
