package com.shivam.tree.mousam.location;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyGoogleLocation {

    JSONObject json;
    boolean isSuccess= false;

    public MyGoogleLocation(String s) {
        try {
            json = new JSONObject(s);
        } catch (Exception e) {
        }
        this.json = json;
        findAll();
    }


    public void findAll() {
        try {
            status = json.getString("status");

            JSONArray jarr = json.getJSONArray("results");
            JSONObject job = jarr.getJSONObject(0);
            JSONArray jarr1 = job.getJSONArray("address_components");

            address = job.getString("formatted_address");

            JSONObject geo = job.getJSONObject("geometry");
            JSONObject gloc = geo.getJSONObject("location");
            lat = gloc.getDouble("lat");
            lng = gloc.getDouble("lng");


            for (int i = 0; i < jarr1.length(); i++) {
                JSONObject jo = jarr1.getJSONObject(i);
                String temp = jo.getString("types");
                String val = jo.getString("long_name");
                String val2 = jo.getString("short_name");
                setData(temp, val, val2);
            }

            String ttmp = job.getString("types");
            String tempp = ttmp.substring(ttmp.indexOf("\"") + 1, ttmp.length());
            type = tempp.substring(0, tempp.indexOf("\""));

            isSuccess= true;
        } catch (Exception e) {
            Log.d("Error Shivam ", e.toString());
            isSuccess= false;
        }
    }

    public boolean isSuccess(){
        return isSuccess;
    }


    private void setData(String type, String value, String value2) {
        String temp = type.substring(type.indexOf("\"") + 1, type.length());
        type = temp.substring(0, temp.indexOf("\""));
        mtype = type;
        switch (type) {
            case "postal_code":
                pincode = value;
                break;
            case "country":
                country = value;
                countryCode= value2;
                break;
            case "administrative_area_level_1":
                state = value;
                break;
            case "administrative_area_level_2":
                distric = value;
                break;
            case "locality":
                city = value;
                break;
            case "establishment":
                famous_place = value;
                break;
        }
    }

    String mtype;

    public String getMtype() {
        return mtype;
    }


    double lat, lng;
    String famous_place, status, country, countryCode, state, distric, city, pincode, address, placeId, type;

    public String getStatus() {
        return status;
    }

    public String getFamous_place() {
        return famous_place;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getDistric() {
        return distric;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAddress() {
        return address;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getType() {
        return type;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}//classEND
