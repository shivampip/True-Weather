package com.shivam.tree.mousam.shiv;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public interface ShivWeatherListener<T> {

    void onResponse(ShivWeather myWea);

    void onFailure(String err);

}
