package com.shivam.tree.mousam.location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.DialogCallBack;
import com.shivam.tree.mousam.R;

import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public class LocationDialog {

    Context context;
    DialogCallBack dialogCallBack;
    AlertDialog ad;
    TextView locationTv;
    ActionProcessButton currnetLocationAPB;
    EditText cityEt;
    ActionProcessButton searchCityAPB;
    Button proceedB;
    MyGoogleLocation myGoogleLocation;

    public LocationDialog(Context context, DialogCallBack dialogCallBack) {
        this.context = context;
        this.dialogCallBack = dialogCallBack;
    }


    public void show() {

        View layout = LayoutInflater.from(context).inflate(R.layout.location_dialog_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        locationTv = (TextView) layout.findViewById(R.id.locationTv);
        currnetLocationAPB = (ActionProcessButton) layout.findViewById(R.id.currentLocationAPB);
        searchCityAPB = (ActionProcessButton) layout.findViewById(R.id.searchCityAPB);
        cityEt = (EditText) layout.findViewById(R.id.cityEt);
        proceedB = (Button) layout.findViewById(R.id.locDiaProceedB);

        currnetLocationAPB.setMode(ActionProcessButton.Mode.ENDLESS);
        searchCityAPB.setMode(ActionProcessButton.Mode.ENDLESS);


        currnetLocationAPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Const.isNetworkAvailable(context)){
                    locationTv.setText("Please Enable Internet");
                    return;
                }

                currnetLocationAPB.setProgress(1);
                SearchMyLocation sml = new SearchMyLocation(new OnLocationFoundListener() {
                    @Override
                    public void onLocationFound(double lat, double lon) {
                        //locationTv.setText("lat: " + lat + "\nlon " + lon);
                        currnetLocationAPB.setText("Decoding Location....");
                    }

                    @Override
                    public void onDecoded(MyGoogleLocation mgl) {
                        if (mgl != null) {
                            currnetLocationAPB.setProgress(100);
                            locationTv.setText(mgl.getAddress());
                            myGoogleLocation = mgl;
                            //proceedB.setVisibility(View.VISIBLE);
                            ad.dismiss();
                            dialogCallBack.onCallback(myGoogleLocation);
                        }
                    }
                });
                sml.execute();
            }
        });

        searchCityAPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Const.isNetworkAvailable(context)){
                    locationTv.setText("Please Enable Internet");
                    return;
                }

                searchCityAPB.setProgress(1);
                String cityName = cityEt.getText().toString();

                CallGoogle cg = new CallGoogle(new OnWebResponse() {
                    @Override
                    public void onResponse(String data) {
                        MyGoogleLocation mgl = new MyGoogleLocation(data);
                        if (mgl.isSuccess()) {
                            locationTv.setText(mgl.getAddress());
                            myGoogleLocation = mgl;
                            searchCityAPB.setProgress(100);
                            proceedB.setVisibility(View.VISIBLE);
                        }else{
                            locationTv.setText("Invalid City Name, Try again.");
                            searchCityAPB.setProgress(0);
                            searchCityAPB.setText("Search City");
                            cityEt.setText("");
                            cityEt.requestFocus();
                        }
                    }
                });
                if (!cityName.equals("")) {
                    cg.execute("address=", cityName);
                } else {
                    locationTv.setText("Please Enter the data.");
                    searchCityAPB.setProgress(0);
                    searchCityAPB.setText("Search City");
                    cityEt.setText("");
                    cityEt.requestFocus();
                }
            }
        });

        proceedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                dialogCallBack.onCallback(myGoogleLocation);
            }
        });


        builder.setView(layout);
        ad = builder.create();
        ad.show();

        //REMOVE FOCUS is DONE
        //See in Layout file above edittext

    }//showEND


    class SearchMyLocation extends AsyncTask<Void, Double, Double> {

        LocationManager locMan;
        OnLocationFoundListener listener;

        public SearchMyLocation(OnLocationFoundListener onLocationFoundListener) {
            this.listener = onLocationFoundListener;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            locMan = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            try {
                locMan.requestSingleUpdate(LocationManager.GPS_PROVIDER, locListerner, null);
            } catch (SecurityException e) {
                showError("Permission Denied.");
            }
        }

        LocationListener locListerner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                listener.onLocationFound(lat, lng);
                callGoogle("latlng=", lat + "," + lng);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                currnetLocationAPB.performClick();
            }

            @Override
            public void onProviderDisabled(String s) {
                showError("GPS Disabled.");
                askGPSOn();
            }
        };

        public void callGoogle(String para, String city) {
            CallGoogle cg = new CallGoogle(new OnWebResponse() {
                @Override
                public void onResponse(String data) {
                    Log.d("SSHHIIVVAAMM", "OMGOOGLE: " + data);
                    MyGoogleLocation mgl = new MyGoogleLocation(data);
                    listener.onDecoded(mgl);
                }
            });
            cg.execute(para, city);
        }
    }//innerClassEND


    public class CallGoogle extends AsyncTask<String, Void, String> {
        OnWebResponse onWebResponse;

        public CallGoogle(OnWebResponse onWebResponse) {
            this.onWebResponse = onWebResponse;
        }

        @Override
        protected String doInBackground(String... values) {
            String para = values[0];
            String city = values[1];
            String preUrl = "https://maps.googleapis.com/maps/api/geocode/json?" + para;
            String rowUrl = "";
            try {
                rowUrl = URLEncoder.encode(city, "utf-8");
            } catch (Exception e) {
            }
            String url = preUrl + rowUrl;
            String data = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                data = response.body().string();
            } catch (Exception e) {
                data = e + "";
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            onWebResponse.onResponse(s);
        }
    }//callGoogleEND


    public void showError(String msg) {
        locationTv.setText(msg);
        currnetLocationAPB.setProgress(-1);
        currnetLocationAPB.setText(msg);
    }



    public void askGPSOn(){
        final AlertDialog ad[]= new AlertDialog[1];
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setMessage("For accessing your current location Pleast turn on GPS ");
        builder.setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad[0].dismiss();
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad[0].dismiss();
            }
        });
        ad[0]= builder.create();
        ad[0].show();
    }

    public void display(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}//classEND


