package com.shivam.tree.mousam.shiv;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public class ShivOWAS {

    String baseUrl = "http://api.openweathermap.org/data/2.5/";

    public final static String RES_FORCASE = "forecast";
    public final static String RES_CURRENT_WEATHER = "weather";


    String apiKey;

    public ShivOWAS(String apiKey, String resType) {
        this.apiKey = apiKey;
        baseUrl = baseUrl + resType + "?appid=" + apiKey;
    }

    public void setCity(String city) {
        baseUrl += "&q=" + city;
    }

    public void setLatLon(double lat, double lon) {
        baseUrl += "&lat=" + lat + "&lon=" + lon;
    }


    public void setCurrentWeatherListener(final ShivWeatherListener<ShivWeather> shivWeatherListener) {

        CallMyOk callMyOk = new CallMyOk();
        callMyOk.execute(new ResponseListener() {
            @Override
            public void onResponse(String response) {
                ShivWeather shivWeather = new ShivWeather(response);
                shivWeatherListener.onResponse(shivWeather);
            }

            @Override
            public void onFail(String reason) {
                shivWeatherListener.onFailure(reason);
            }
        });


    }


    public void setWeatherForcastListener(final WeatherForcastListener weatherForcastListener) {
        CallMyOk callMyOk = new CallMyOk();
        callMyOk.execute(new ResponseListener() {
            @Override
            public void onResponse(String response) {
                WeatherItem items[] = ShivWeatherList.decodeData(response);
                if (items != null) {
                    weatherForcastListener.onResponse(items);
                } else {
                    weatherForcastListener.onFailure(response);
                }
            }

            @Override
            public void onFail(String reason) {
                weatherForcastListener.onFailure(reason);
            }
        });
    }


    class CallMyOk extends AsyncTask<ResponseListener, String, String> {

        OkHttpClient client;
        ResponseListener responseListener;
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new OkHttpClient();
        }

        @Override
        protected String doInBackground(ResponseListener... listeners) {
            responseListener = listeners[0];
            String url = baseUrl;
            String response;
            try {
                response = run(url);
                isSuccess = true;
            } catch (Exception e) {
                isSuccess = false;
                response = e.getMessage();
            }
            return response;
        }

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (isSuccess) {
                if(s.length()>50) {
                    responseListener.onResponse(s);
                }else {
                    responseListener.onFail(s);
                }
            } else {
                responseListener.onFail(s);
            }
        }
    }//innerClassEND


}//classEND
