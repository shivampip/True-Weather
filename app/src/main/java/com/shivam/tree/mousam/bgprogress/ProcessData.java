package com.shivam.tree.mousam.bgprogress;

import android.content.Context;

import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.backup.BackupData;
import com.shivam.tree.mousam.shiv.WeahterDay;
import com.shivam.tree.mousam.shiv.WeatherItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sittu Agrawal on 16-01-2017.
 */

public class ProcessData {

    Context context;
    BackupData data;
    int icon;
    String title;
    String details;
    Date dt;
    String formatAdd;


    final static int TODAY= 0;
    final static int TOMORROW= 1;
    final static int DAY_AFTER_TOMORROW= 2;


    public ProcessData(Context context, BackupData data, int when) {
        this.context= context;
        this.data = data;
        startProcessing(when);
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setDesc(Condition condition, WeahterDay day) {
        this.details = Writer.prepareDetails(context, condition, day);
    }

    public int getIcon() {
        return icon;
    }

    public String getDetails() {
        return details;
    }

    private void startProcessing(int when) {

        formatAdd = data.getFormatAdd();

        ArrayList<WeahterDay> days = getWeatherDays();

        WeahterDay today = days.get(when);

        ArrayList<WeatherItem> items = today.getItems();

        Condition condition= getMax(items);

        int iconRes= getTipIcon(condition.getMain(), today.getTempMax(), today.getTempMin());
        //setIcon(getMaxIcon(items).getIconRes());
        if(iconRes!=0){
            setIcon(iconRes);
        }else {
            setIcon(getMaxIcon(items).getIconRes());
        }
        setDesc(condition, today);

    }//startProcessingEND


    private int getTipIcon(String mainW, double tempMax, double tempMin){
        int resIcon= 0;
        switch (mainW){
            case "Clouds":
                //coludy
                break;
            case "Rain":
                resIcon= R.drawable.tip_rain;
                break;
            case "Clear":
                resIcon= R.drawable.tip_clear;
                break;
            case "Snow":
                resIcon= R.drawable.tip_winter;
                break;
        }

        if(tempMax>Writer.HOT_NORMAL){
            resIcon= R.drawable.tip_summer;
        }else if(tempMin<Writer.COLD_NORMAL){
            resIcon= R.drawable.tip_winter;
        }
        return resIcon;
    }


    private ArrayList<WeahterDay> getWeatherDays() {
        WeatherItem[] items = data.getWeatherItems();
        Calendar cal = Calendar.getInstance();
        ArrayList<WeahterDay> days = new ArrayList<>();

        int cDay = -1;
        for (WeatherItem item : items) {
            if (item != null) {
                cal.setTimeInMillis(item.getTimestmp());
                int tday = cal.get(Calendar.DAY_OF_MONTH);
                if (cDay != tday) {
                    cDay = tday;
                    WeahterDay wd = new WeahterDay();
                    wd.addItem(item);
                    days.add(wd);
                } else {
                    WeahterDay wd = days.remove(days.size() - 1);
                    wd.addItem(item);
                    days.add(wd);
                }
            }
        }//forEND
        return days;
    }


    private Condition getMax(ArrayList<WeatherItem> list) {
        ArrayList<Condition> mList = new ArrayList<>();
        for (WeatherItem item : list) {
            boolean isFound = false;
            for (Condition c : mList) {
                if (c.getName().equals(item.getDescription())) {
                    c.incrCount();
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                Condition con = new Condition();
                con.setName(item.getDescription());
                con.setIconRes(item.getIconRes());
                con.setDt(new Date(item.getTimestmp()));
                con.setMain(item.getMainDesc());
                con.incrCount();
                mList.add(con);
            }

        }//forEND

        Condition maxCon= mList.get(0);
        for (Condition condition : mList) {
            C.d("Condition:- " + condition.getName() + " - " + condition.getCount());
            if(condition.getCount()>maxCon.getCount()){
                maxCon= condition;
            }
        }

        C.d("Condition MAX is "+maxCon.getName()+" - "+maxCon.getCount()+" with "+maxCon.getIconRes());

        return maxCon;
    }//getMaxEND


    private Condition getMaxIcon(ArrayList<WeatherItem> list) {
        ArrayList<Condition> mList = new ArrayList<>();
        for (WeatherItem item : list) {
            boolean isFound = false;
            for (Condition c : mList) {
                if (c.getIconRes() == item.getIconRes()) {
                    c.incrCount();
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                Condition con = new Condition();
                con.setIconRes(item.getIconRes());
                con.incrCount();
                mList.add(con);
            }

        }//forEND

        Condition maxCon = mList.get(0);
        for (Condition condition : mList) {
            if (condition.getCount() > maxCon.getCount()) {
                maxCon = condition;
            }
        }

        Condition maxCon2 = null;
        for (Condition condition : mList) {
            if (condition.getIconRes() == maxCon.getIconRes()) {
                continue;
            }
            if (condition.getCount() == maxCon.getCount()) {
                maxCon2 = condition;
            }
        }

        C.d("Condition MAX iiccoonn is " + maxCon.getIconRes() + " - " + maxCon.getCount());

        if (maxCon2 != null) {
            int a= getPriyority(maxCon.getIconRes());
            int b= getPriyority(maxCon2.getIconRes());
            if(a>b){
                return maxCon;
            }else {
                return maxCon2;
            }
        }

        return maxCon;
    }//getMaxEND


    private int getPriyority(int res) {
        int p = 0;
        switch (res) {
            case R.drawable.shiv11d:
                p = 18;
                break;
            case R.drawable.shiv11n:
                p = 17;
                break;
            case R.drawable.shiv13d:
                p = 16;
                break;
            case R.drawable.shiv13n:
                p = 15;
                break;
            case R.drawable.shiv10d:
                p = 14;
                break;
            case R.drawable.shiv10n:
                p = 13;
                break;
            case R.drawable.shiv09d:
                p = 12;
                break;
            case R.drawable.shiv09n:
                p = 11;
                break;
            case R.drawable.shiv04d:
                p = 10;
                break;
            case R.drawable.shiv04n:
                p = 9;
                break;
            case R.drawable.shiv03d:
                p = 8;
                break;
            case R.drawable.shiv03n:
                p = 7;
                break;
            case R.drawable.shiv50d:
                p = 6;
                break;
            case R.drawable.shiv50n:
                p = 5;
                break;
            case R.drawable.shiv02d:
                p = 4;
                break;
            case R.drawable.shiv02n:
                p = 3;
                break;
            case R.drawable.shiv01d:
                p = 2;
                break;
            case R.drawable.shiv01n:
                p = 1;
                break;
            default:
                p=0;
                break;
        }
        return p;
    }


}//classsEND
