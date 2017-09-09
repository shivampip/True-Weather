package com.shivam.tree.mousam.shiv;

import android.util.Log;

import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.bgprogress.C;
import com.shivam.tree.mousam.bgprogress.Condition;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sittu Agrawal on 08-01-2017.
 */

public class WeahterDay {

    ArrayList<WeatherItem> items = new ArrayList<>();

    double tempMax = -1000, tempMin = 1000;
    int iconRes;
    String description;
    long timestmp;

    int humidity = 0;
    double windSpeed = 0;
    int index4hw = 0;


    public void addItem(WeatherItem item) {
        if (item == null) {
            Log.d("SSHHIIVVAAMM", "ITEM is NULL");
            return;
        }
        this.items.add(item);

        this.timestmp = item.getTimestmp();

        if (item.getTempMax() > tempMax) {
            tempMax = item.getTempMax();
        }
        if (item.getTempMin() < tempMin) {
            tempMin = item.getTempMin();
        }

        humidity += item.getHumadity();
        windSpeed += item.getWindSpeed();
        index4hw++;

    }//addItemEND

    public ArrayList<WeatherItem> getItems() {
        return this.items;
    }


    public long getTimestmp() {
        return this.timestmp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }


    public int getHumidity() {
        return humidity / index4hw;
    }

    public double getWindSpeed() {
        return windSpeed / index4hw;
    }


    public int getIconRes() {
        Condition con = getMaxIcon(getItems());
        return con.getIconRes();
    }

    public String getDescription() {
        Condition con = getMax(getItems());
        return con.getName();
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
                con.incrCount();
                mList.add(con);
            }

        }//forEND

        Condition maxCon = mList.get(0);
        for (Condition condition : mList) {
            C.d("Condition:- " + condition.getName() + " - " + condition.getCount());
            if (condition.getCount() > maxCon.getCount()) {
                maxCon = condition;
            }
        }

        C.d("Condition MAX is " + maxCon.getName() + " - " + maxCon.getCount());

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


}//classEND
