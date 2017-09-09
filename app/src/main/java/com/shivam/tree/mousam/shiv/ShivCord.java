package com.shivam.tree.mousam.shiv;

import java.io.Serializable;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public class ShivCord{

    private double lat,lon;

    public ShivCord(double lat, double lon){
        this.lat= lat;
        this.lon= lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
