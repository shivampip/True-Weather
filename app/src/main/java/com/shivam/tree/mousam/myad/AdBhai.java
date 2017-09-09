package com.shivam.tree.mousam.myad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.shivam.tree.mousam.R;

/**
 * Created by Sittu Agrawal on 20-01-2017.
 */

public class AdBhai {

    Context context;
    Activity activity;
    InterstitialAd intAd;

    public AdBhai(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        if(shouldShowAd()){
            decrCount();
            this.intAd = createNewIntAd();
            loadIntAdd();
        }
        incrCount();
    }


    private InterstitialAd createNewIntAd() {
        InterstitialAd intAd = new InterstitialAd(context);
        intAd.setAdUnitId(context.getString(R.string.tip_click_int_ad_id));
        intAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                //levelTwo();
            }
        });
        return intAd;
    }


    public void showIntAdd() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (intAd != null && intAd.isLoaded()) {
            intAd.show();
            incrCount();
        } else {
            //levelTwo();
        }
    }

    private void loadIntAdd() {
        // Disable the  level two button and load the ad.
        AdRequest adRequest = new AdRequest.Builder().build();
        intAd.loadAd(adRequest);
    }


    private static String AD_COUNT = "adCount";
    private static int MAX_COUNT_TO_SHOW_ADD = 3;

    private void incrCount() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int pCount = sp.getInt(AD_COUNT, 0);
        pCount++;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(AD_COUNT, pCount);
        editor.commit();
        d("iCount is "+pCount);
    }

    private void decrCount() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int pCount = sp.getInt(AD_COUNT, 0);
        pCount--;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(AD_COUNT, pCount);
        editor.commit();
        d("dCount is "+pCount);
    }

    private boolean shouldShowAd() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int count = sp.getInt(AD_COUNT, -1);
        d("Count is "+count);
        if (count != 0 && count % MAX_COUNT_TO_SHOW_ADD == 0) {
            return true;
        } else {
            return false;
        }
    }


    public void d(String msg){
        Log.d("OOOMMM", msg);
    }
}//classEND
