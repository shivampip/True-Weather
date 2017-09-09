package com.shivam.tree.mousam.shiv;

import com.shivam.tree.mousam.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Sittu Agrawal on 07-01-2017.
 */

public class WeatherItem {

    boolean isSuccess;
    String error = null;

    long timestmp;
    double temp, tempMax, tempMin;
    int humadity;
    double pressure;
    double windSpeed, windDeg;
    int iconRes, id;
    String description, iconUrl, mainDesc;

    public WeatherItem(JSONObject root) {

        try {

            timestmp = root.getLong("dt");

            JSONObject main = root.getJSONObject("main");
            temp = main.getDouble("temp");
            tempMin = main.getDouble("temp_min");
            tempMax = main.getDouble("temp_max");
            pressure = main.getDouble("pressure");
            humadity = main.getInt("humidity");

            JSONArray weather = root.getJSONArray("weather");
            JSONObject weatherObj = weather.getJSONObject(0);
            description = weatherObj.getString("description");
            iconUrl = weatherObj.getString("icon");
            id= weatherObj.getInt("id");
            mainDesc= weatherObj.getString("main");

            JSONObject wind = root.getJSONObject("wind");
            windSpeed = wind.getDouble("speed");
            windDeg = wind.getDouble("deg");

            isSuccess = true;
        } catch (JSONException e) {
            isSuccess = false;
            error = e + "";
        }

    }//constEND

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public long getTimestmp() {
        return timestmp * 1000;
    }

    public double getTemp() {
        return temp- 273.15;
    }


    public double getTempMax() {
        return tempMax - 273.15;
    }

    public double getTempMin() {
        return tempMin - 273.15;
    }

    public int getHumadity() {
        return humadity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public int getIconRes() {
        return Const.getIconRes(iconUrl);
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getMainDesc() {
        return mainDesc;
    }
}//classEND
