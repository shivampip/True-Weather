package com.shivam.tree.mousam.shiv;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sittu Agrawal on 07-01-2017.
 */

public class ShivWeatherList {


    public static WeatherItem[] decodeData(String rowData) {
        WeatherItem item[];
        try {
            JSONObject root = new JSONObject(rowData);
            JSONArray list = root.getJSONArray("list");
            Log.d("SSHHIIVVAAMM", "Length is "+list.length());
            item = new WeatherItem[list.length()];
            for (int i = 0; i < list.length(); i++) {
                JSONObject json = list.getJSONObject(i);
                item[i] = new WeatherItem(json);
            }
        } catch (JSONException e) {
            item= null;
        }
        return item;
    }//metEND


}//classEND
