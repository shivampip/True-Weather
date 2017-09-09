package com.shivam.tree.mousam.backup;

import android.util.Log;

import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.shiv.ShivWeather;
import com.shivam.tree.mousam.shiv.WeatherItem;

/**
 * Created by Sittu Agrawal on 14-01-2017.
 */

public class BackupData {

    WeatherItem []weatherItems;
    ShivWeather shivWeather;
    String formatAdd;

    public WeatherItem[] getWeatherItems() {
        return weatherItems;
    }

    public void setWeatherItems(WeatherItem[] weatherItems) {
        this.weatherItems = weatherItems;
    }

    public String getFormatAdd() {
        return formatAdd;
    }

    public void setFormatAdd(String formatAdd) {
        this.formatAdd = formatAdd;
    }

    public ShivWeather getShivWeather() {
        return shivWeather;
    }

    public void setShivWeather(ShivWeather shivWeather) {
        this.shivWeather = shivWeather;
    }
}//classEND
