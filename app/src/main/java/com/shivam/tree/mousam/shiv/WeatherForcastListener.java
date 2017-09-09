package com.shivam.tree.mousam.shiv;

/**
 * Created by Sittu Agrawal on 07-01-2017.
 */

public interface WeatherForcastListener {

    void onResponse(WeatherItem[] items);

    void onFailure(String err);

}
