package com.shivam.tree.mousam.location;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public interface OnLocationFoundListener {

    public abstract void onLocationFound(double lat, double lon);

    public abstract void onDecoded(MyGoogleLocation mgl);


}
