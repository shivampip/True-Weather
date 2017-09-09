package com.shivam.tree.mousam.shiv;

import android.icu.math.BigDecimal;
import android.util.Log;

import com.shivam.tree.mousam.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public class ShivWeather {

    String rawRes;
    double temp, tempMax, tempMin;
    int humadity;
    int pressure;
    double windSpeed, windDeg;
    int iconRes;
    String description, iconUrl;
    String city;

    public ShivWeather(String rawRes){
        this.rawRes= rawRes;
        try {
            JSONObject root= new JSONObject(rawRes);
            JSONArray weather= root.getJSONArray("weather");
            JSONObject weather1= weather.getJSONObject(0);
            this.description= weather1.getString("description");
            this.iconUrl= weather1.getString("icon");

            JSONObject main= root.getJSONObject("main");
            this.temp= main.getDouble("temp");
            this.tempMax= main.getDouble("temp_max");
            this.tempMin= main.getDouble("temp_min");
            this.humadity= main.getInt("humidity");
            this.pressure= main.getInt("pressure");

            JSONObject wind= root.getJSONObject("wind");
            this.windSpeed= wind.getDouble("speed");
            this.windDeg= wind.getDouble("deg");

            this.city= root.getString("name");

        } catch (JSONException e) {
            Log.d("SSHHIIVVAAMM", "IN ShivWeather:- "+e);
        }

    }//consEND

    public String getRawRes() {
        return rawRes;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getCity(){
        return city;
    }

    public String getDescription() {
        return description;
    }

    public int getIconRes() {
        return Const.getIconRes(iconUrl);
    }

    public double getWindDeg() {
        return windDeg;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumadity() {
        return humadity;
    }

    public double getTempMin() {
        return tempMin- 273.15;
    }

    public double getTempMax() {
        return tempMax- 273.15;
    }

    public double getTemp() {
        return temp- 273.15;
    }
}//classEND
