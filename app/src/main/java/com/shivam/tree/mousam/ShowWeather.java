package com.shivam.tree.mousam;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.gms.ads.MobileAds;
import com.shivam.tree.mousam.about.About;
import com.shivam.tree.mousam.backup.Backup;
import com.shivam.tree.mousam.backup.BackupData;
import com.shivam.tree.mousam.bgprogress.WeatherReport;
import com.shivam.tree.mousam.fcm.ProcessFCM;
import com.shivam.tree.mousam.fcm.ShowDialog;
import com.shivam.tree.mousam.graph.MyData;
import com.shivam.tree.mousam.graph.WeatherGraph;
import com.shivam.tree.mousam.location.LocationDialog;
import com.shivam.tree.mousam.location.MyGoogleLocation;
import com.shivam.tree.mousam.security_tips.SecurityTips;
import com.shivam.tree.mousam.shiv.ShivOWAS;
import com.shivam.tree.mousam.shiv.ShivWeather;
import com.shivam.tree.mousam.shiv.ShivWeatherListener;
import com.shivam.tree.mousam.shiv.WeahterDay;
import com.shivam.tree.mousam.shiv.WeatherForcastListener;
import com.shivam.tree.mousam.shiv.WeatherItem;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowWeather extends AppCompatActivity {

    final int SPLASH_TIME = 3000;
    final int BLUR_UPPER = 100;
    final int BLUR_LOWER = 10;

    RelativeLayout splashRL;
    TextView splashMainTv;
    RealtimeBlurView splashBlur;

    LinearLayout upperLay;
    LinearLayout forcastLL;

    RelativeLayout singleRL, doubleRL;
    LinearLayout huAndWSLL;
    RelativeLayout notConfiguredRL;
    TextView ncTv;
    Button ncB;
    RealtimeBlurView blurRBV;

    ImageView singleMainIconIv;
    TextView singleTempTv, singleMaxTempTv, singleMinTempTv, singleDescriptionTv;

    ImageView doubleMainIconIv;
    TextView doubleMaxTempTv, doubleMinTempTv, doubleDescriptionTv;

    TextView humadityTv, windSpeedTv, dateTv;
    TextView cityTv;
    ImageButton locaionIB, tipIB, menuIB;

    View graphTitleV, fullDetailsTitleV, mapTitleV;
    LineChart myLineChart;
    LinearLayout myFullDetailsLL;
    ImageView mapIV;

    Typeface customFont1, customFont2;
    ShivOWAS shivOWAS;
    Backup backup;
    BackupData backupData;

    boolean isCurWeaLoaded = false, isForcastUILoaded = false, isGraphLoaded = false, isFullLoaded = false, isError = false;

    //final String API_KEY = "dc1558abc2d446c6d66bbc9b361f86b0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        showSplash();

        forcastLL = (LinearLayout) findViewById(R.id.forcastLL);
        upperLay = (LinearLayout) findViewById(R.id.upperLay);
        locaionIB = (ImageButton) findViewById(R.id.locationIB);
        tipIB = (ImageButton) findViewById(R.id.tipIB);
        cityTv = (TextView) findViewById(R.id.cityTv);
        humadityTv = (TextView) findViewById(R.id.humidityTv);
        windSpeedTv = (TextView) findViewById(R.id.windSpeedTv);
        dateTv = (TextView) findViewById(R.id.dateTv);
        locaionIB.setOnClickListener(locationListener);
        cityTv.setOnClickListener(locationListener);
        tipIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipIB.setEnabled(false);
                showTip();
            }
        });
        huAndWSLL = (LinearLayout) findViewById(R.id.huAndWSLL);
        blurRBV = (RealtimeBlurView) findViewById(R.id.blurRBV);
        menuIB = (ImageButton) findViewById(R.id.menuIB);


        notConfiguredRL = (RelativeLayout) findViewById(R.id.notConfiguredRL);
        ncTv = (TextView) findViewById(R.id.notConfiguredTv);
        ncB = (Button) findViewById(R.id.notConfiguredB);

        singleDescriptionTv = (TextView) findViewById(R.id.singleDescripitonTv);
        singleMainIconIv = (ImageView) findViewById(R.id.singleMainIconIV);
        singleTempTv = (TextView) findViewById(R.id.singleTempTv);
        singleMaxTempTv = (TextView) findViewById(R.id.singleMaxTempTv);
        singleMinTempTv = (TextView) findViewById(R.id.singleMinTempTv);
        singleRL = (RelativeLayout) findViewById(R.id.singleRL);

        doubleMainIconIv = (ImageView) findViewById(R.id.doubleMainIconIV);
        doubleMaxTempTv = (TextView) findViewById(R.id.doubleMaxTempTv);
        doubleMinTempTv = (TextView) findViewById(R.id.doubleMinTempTv);
        doubleDescriptionTv = (TextView) findViewById(R.id.doubleDescriptionTv);
        doubleRL = (RelativeLayout) findViewById(R.id.doubleRL);

        init();
        showMyDialog();

    }//onCreateEND


    private void showSplash() {
        splashRL = (RelativeLayout) findViewById(R.id.splashRL);
        splashMainTv = (TextView) findViewById(R.id.splashMainTv);
        splashBlur = (RealtimeBlurView) findViewById(R.id.splashBlur);
        customFont1 = Const.getCustomTypeface(this, Const.TF_JUNGE_REGULAR);
        customFont2 = Const.getCustomTypeface(this, Const.TF_NERKSHIRE_SWASH);

        SplashLoader sl = new SplashLoader();
        sl.execute(splashBlur);
    }


    public void showMyDialog() {
        Intent intent = getIntent();
        int code = intent.getIntExtra(Const.DIALOG_CODE, -1);
        switch (code) {
            case Const.DC_TIP:
                WeatherReport wr = new WeatherReport(this, this);
                break;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String type = sp.getString(ProcessFCM.TYPE, null);
        if (type == null) {
            return;
        }
        if (type.equals(ProcessFCM.TYPE_WISHES)) {
            ShowDialog.show(this, ProcessFCM.getDialogData(this));
        }
    }

    private void init() {
        backup = new Backup(this);
        backupData = new BackupData();

        MobileAds.initialize(getApplicationContext(), getString(R.string.app_id));


        singleRL.setVisibility(View.GONE);
        doubleRL.setVisibility(View.GONE);
        cityTv.setText("Select Location");
        dateTv.setText(new Date().toString());
        huAndWSLL.setVisibility(View.GONE);
        notConfiguredRL.setVisibility(View.GONE);

        menuIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingIn = new Intent(ShowWeather.this, Setting.class);
                ShowWeather.this.startActivity(settingIn);
            }
        });


        blurRBV.setBlurRadius(BLUR_LOWER);
        //blurRBV.setOverlayColor(Const.getAIColor());


        syncData();
    }

    private void showTip() {
        WeatherReport wr = new WeatherReport(ShowWeather.this, ShowWeather.this);
        tipIB.setEnabled(true);
    }


    private void syncData() {
        notConfiguredRL.setVisibility(View.GONE);
        isCurWeaLoaded = false;
        isForcastUILoaded = false;
        isFullLoaded = false;
        isGraphLoaded = false;
        isError = false;

        if (backup.isBackupFresh()) {
            if (backup.isBackupAvailable()) {
                //display("last synced " + backup.getBackupDuration() + " ago");
                restore();
            }
        } else if (Const.isNetworkAvailable(this)) {
            //Partial Backup (Only City and Formated address
            if (backup.isBackupAvailOne(Backup.CITY)) {
                String city = backup.restoreOne(Backup.CITY);
                String formatAdd = backup.restoreOne(Backup.FORMAT_ADD);
                formatAddress = formatAdd;
                getJankari(city, formatAdd);
                getForcastJankari(city, formatAdd);
            } else {
                askLocation();
                notConfiguredRL.setVisibility(View.VISIBLE);
                isError = true;
                ncTv.setText("No Location Selected");
                ncB.setText("Select Location");
                ncB.setOnClickListener(locationListener);
            }
        } else {
            display("Network Not Available");
            notConfiguredRL.setVisibility(View.VISIBLE);
            isError = true;
            ncTv.setText("Network Not Available");
            ncB.setText("Retry");
            ncB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    display("Retrying....");
                    syncData();
                }
            });
        }
    }//syncDataEND


    public void getJankari(String city, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getRanAPIKey(), ShivOWAS.RES_CURRENT_WEATHER);
        shivOWAS.setCity(city);
        getJankari(formatAdd);
    }

    //Getting Current Weather Info
    public void getJankari(final String formatAdd) {
        shivOWAS.setCurrentWeatherListener(new ShivWeatherListener<ShivWeather>() {
            @Override
            public void onResponse(ShivWeather myWea) {
                Log.d(Const.TAG2, "Results from :- " + myWea.getCity());
                showCurrentWeather(myWea, formatAdd);
                backup(myWea, formatAdd);
            }

            @Override
            public void onFailure(String err) {
                serverErr(err, CURRENT);
            }
        });

    }

    public void getForcastJankari(String city, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getRanAPIKey(), ShivOWAS.RES_FORCASE);
        shivOWAS.setCity(city);
        getForcastJankari(formatAdd);
    }

    public void getForcastJankari(final String formatAdd) {
        forcastLL.removeAllViews();
        shivOWAS.setWeatherForcastListener(new WeatherForcastListener() {
            @Override
            public void onResponse(WeatherItem[] items) {
                processForcastInfo(items);
                backup(items);
            }

            @Override
            public void onFailure(String err) {
                serverErr(err, FORECAST);
            }
        });
    }


    //#####################################################################
    //Backup ANd Resotre
    private void backup(ShivWeather shivWeather, String formatAdd) {
        backupData.setShivWeather(shivWeather);
        backupData.setFormatAdd(formatAdd);
    }

    private void backup(WeatherItem[] items) {
        backupData.setWeatherItems(items);
        backup.backup(backupData);
    }

    private void restore() {
        //Call showCurrentWeather(...)
        //Call precessForcastInfo(...)
        BackupData backupData = backup.restore();
        formatAddress = backupData.getFormatAdd();
        showCurrentWeather(backupData.getShivWeather(), backupData.getFormatAdd());
        processForcastInfo(backupData.getWeatherItems());
    }

    //#####################################################################
    //From here Non Internet Work Starts (Start Restore from here
    public void showCurrentWeather(ShivWeather myWea, String formatAdd) {

        singleRL.setVisibility(View.VISIBLE);
        doubleRL.setVisibility(View.GONE);
        huAndWSLL.setVisibility(View.VISIBLE);

        Date dt = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy , hh:mm aaa");
        dateTv.setText(simpleDateFormat.format(dt));
        singleTempTv.setText(String.format("%.0f", myWea.getTemp()) + Const.DEGREE);
        humadityTv.setText(myWea.getHumadity() + "%");
        double myWindSpeed = myWea.getWindSpeed() * 60 * 60 / 1000;
        windSpeedTv.setText(String.format("%.2f", myWindSpeed) + " km/h");
        cityTv.setText(formatAdd);
        singleDescriptionTv.setText(toFUpper(myWea.getDescription()));
        singleMainIconIv.setImageResource(myWea.getIconRes());


        if ((myWea.getTempMax() - myWea.getTempMin()) >= 1) {
            singleMaxTempTv.setText(String.format("%.0f", myWea.getTempMax()) + Const.DEGREE);
            singleMinTempTv.setText(String.format("%.0f", myWea.getTempMin()) + Const.DEGREE);
        } else {
            singleMaxTempTv.setText("");
            singleMinTempTv.setText("");
        }


    }

    private void processForcastInfo(WeatherItem[] items) {

        forcastLL.removeAllViews();

        Calendar cal = Calendar.getInstance();
        ArrayList<WeahterDay> days = new ArrayList<>();

        int cDay = -1;
        for (WeatherItem item : items) {
            if (item != null) {
                cal.setTimeInMillis(item.getTimestmp());
                int tday = cal.get(Calendar.DAY_OF_MONTH);
                if (cDay != tday) {
                    cDay = tday;
                    WeahterDay wd = new WeahterDay();
                    wd.addItem(item);
                    days.add(wd);
                } else {
                    WeahterDay wd = days.remove(days.size() - 1);
                    wd.addItem(item);
                    days.add(wd);
                }
            }
        }//forEND


        for (WeahterDay wd : days) {
            addForcastUI(wd);
            for (WeatherItem item : wd.getItems()) {
                cal.setTimeInMillis(item.getTimestmp());
                String dayd = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa");
                String dt = sdf.format(cal.getTime());
            }
        }

        isForcastUILoaded = true;

        //%%%%%%%%$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        final MyData md = new MyData();
        long[] dt = new long[days.get(0).getItems().size()];
        int i = 0;
        for (WeatherItem wi : days.get(0).getItems()) {
            md.add((float) wi.getTemp());
            //md.add(wi.getTimestmp(), (float) wi.getTemp());
            dt[i++] = wi.getTimestmp();


//            Date d= new Date(wi.getTimestmp());
//            float f= (float) wi.getTimestmp();
//            Date dd= new Date((long) f);
//            Log.d(Const.TAG2, "SW Next is "+d.toString());
//            Log.d(Const.TAG2, "MD Next is "+dd.toString());
        }

        try {
            double max = days.get(0).getTempMax();
            double min = days.get(0).getTempMin();
            String tdtd = singleTempTv.getText().toString();
            double cur = Double.parseDouble(tdtd.substring(0, tdtd.length() - 1));
            max = (max < cur) ? cur : max;
            min = (min > cur) ? cur : min;
            if (max - min >= 1) {
                singleMaxTempTv.setText(Const.ARROW_UP + String.format("%.0f", max) + Const.DEGREE);
                singleMinTempTv.setText(Const.ARROW_DOWN + String.format("%.0f", min) + Const.DEGREE);
            } else {
                singleMaxTempTv.setText("");
                singleMinTempTv.setText("");
            }
        } catch (Exception e) {
        }

        showGraph(md, dt);
        isGraphLoaded = true;
        showFullDetails(days.get(0));
        isFullLoaded = true;
        showMap();

    }//processENd

    private void showGraph(MyData myData, long dt[]) {
        removeGraph();
        if (myData.getList().size() <= 3) {
            return;
        }
        graphTitleV = prepareTitleView(graphTitleV, "Hourly Update");
        upperLay.addView(graphTitleV);
        myLineChart = new LineChart(this);
        upperLay.addView(myLineChart);
        WeatherGraph wg = new WeatherGraph(this, myLineChart, myData, dt);
        wg.animateXY(0, 1500);
    }

    private void showFullDetails(WeahterDay weahterDay) {
        removeFullDetails();
        fullDetailsTitleV = prepareTitleView(fullDetailsTitleV, "Full Details");
        upperLay.addView(fullDetailsTitleV);
        myFullDetailsLL = new LinearLayout(this);
        myFullDetailsLL.setOrientation(LinearLayout.VERTICAL);
        upperLay.addView(myFullDetailsLL);
        for (WeatherItem wi : weahterDay.getItems()) {
            addRow(myFullDetailsLL, wi);
        }
    }


    private void addRow(LinearLayout myFullDetailsLL, WeatherItem item) {
        String temp = String.format("%.0f", item.getTemp()) + Const.DEGREE;
        int iconRes = item.getIconRes();
        String desc = item.getDescription();
        Date dt = new Date(item.getTimestmp());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
        String dtv = simpleDateFormat.format(dt);

        View layout = LayoutInflater.from(this).inflate(R.layout.full_details_row, null);

        TextView timeTv = (TextView) layout.findViewById(R.id.rowTimeTv);
        TextView descTv = (TextView) layout.findViewById(R.id.rowDescTv);
        ImageView iconIv = (ImageView) layout.findViewById(R.id.rowIconTv);
        TextView tempTv = (TextView) layout.findViewById(R.id.rowTempTv);

        tempTv.setText(temp);
        iconIv.setImageResource(iconRes);
        descTv.setText(toFUpper(desc));
        timeTv.setText(dtv);
        myFullDetailsLL.addView(layout);
    }


    private void removeGraph() {
        if (myLineChart != null) {
            try {
                upperLay.removeView(graphTitleV);
                upperLay.removeView(myLineChart);
            } catch (Exception e) {
            }
        }
    }

    private void removeFullDetails() {
        if (myFullDetailsLL != null) {
            upperLay.removeView(fullDetailsTitleV);
            upperLay.removeView(myFullDetailsLL);
        }
    }

    private void removeMapIV() {
        try {
            if (mapIV != null) {
                upperLay.removeView(mapIV);
            }
            if (mapTitleV != null) {
                upperLay.removeView(mapTitleV);
            }
        } catch (Exception e) {
        }
    }

    private void showMainDetails(WeahterDay dayItem) {
        singleRL.setVisibility(View.GONE);
        doubleRL.setVisibility(View.VISIBLE);
        huAndWSLL.setVisibility(View.VISIBLE);

        Date dt = new Date(dayItem.getTimestmp());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        dateTv.setText(simpleDateFormat.format(dt));
        humadityTv.setText(dayItem.getHumidity() + "%");
        double myWindSpeed = dayItem.getWindSpeed() * 60 * 60 / 1000;
        windSpeedTv.setText(String.format("%.2f", myWindSpeed) + " km/h");

        doubleMainIconIv.setImageResource(dayItem.getIconRes());
        doubleMaxTempTv.setText(Const.ARROW_UP + String.format("%.0f", dayItem.getTempMax()) + Const.DEGREE);
        doubleDescriptionTv.setText(toFUpper(dayItem.getDescription()));
        doubleMinTempTv.setText(Const.ARROW_DOWN + String.format("%.0f", dayItem.getTempMin()) + Const.DEGREE);

    }


    private View prepareTitleView(View v, String title) {
        v = LayoutInflater.from(this).inflate(R.layout.sub_title, null);
        TextView titleTv = (TextView) v.findViewById(R.id.subTitleTv);
        titleTv.setText(title);
        return v;
    }

    public void addForcastUI(final WeahterDay dayItem) {
        int screenW = Resources.getSystem().getDisplayMetrics().widthPixels;
        int itemW = screenW / 4;

        View layout = LayoutInflater.from(this).inflate(R.layout.sub_item_layout, null);

        TextView subDayTv = (TextView) layout.findViewById(R.id.subDayTv);
        ImageView subIconTv = (ImageView) layout.findViewById(R.id.subIconTv);
        TextView subTempMaxTv = (TextView) layout.findViewById(R.id.subTempMaxTv);
        TextView subTempMinTv = (TextView) layout.findViewById(R.id.subTempMinTv);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dayItem.getTimestmp());
        String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
        subDayTv.setText(dayOfWeek);
        subTempMaxTv.setText("↑" + String.format("%.0f", dayItem.getTempMax()) + "°");
        subTempMinTv.setText("↓" + String.format("%.0f", dayItem.getTempMin()) + "°");

        subIconTv.getLayoutParams().height = itemW;
        subIconTv.setImageResource(dayItem.getIconRes());

        final MyData md = new MyData();
        final long[] dt = new long[dayItem.getItems().size()];
        int i = 0;
        for (WeatherItem wi : dayItem.getItems()) {
            md.add((float) wi.getTemp());
            //md.add(wi.getTimestmp(), (float) wi.getTemp());
            dt[i++] = wi.getTimestmp();
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMainDetails(dayItem);
                showGraph(md, dt);
                showFullDetails(dayItem);
                showMap();
            }
        });

        forcastLL.addView(layout);

    }


    public void getJankari(double lat, double lon, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getRanAPIKey(), ShivOWAS.RES_CURRENT_WEATHER);
        shivOWAS.setLatLon(lat, lon);
        getJankari(formatAdd);
    }

    public void getForcastJankari(double lat, double lon, String formatAdd) {
        shivOWAS = new ShivOWAS(Const.getRanAPIKey(), ShivOWAS.RES_FORCASE);
        shivOWAS.setLatLon(lat, lon);
        getForcastJankari(formatAdd);
    }


    private String toFUpper(String input) {
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }


    private void askLocation() {
        LocationDialog ld = new LocationDialog(ShowWeather.this, new DialogCallBack() {
            @Override
            public void onCallback(Object... objects) {
                notConfiguredRL.setVisibility(View.GONE);
                MyGoogleLocation mgl = (MyGoogleLocation) objects[0];
                double lat = mgl.getLat();
                double lon = mgl.getLng();
                String city = mgl.getCity();
                String state = mgl.getState();
                String country = mgl.getCountry();
                String countryCode = mgl.getCountryCode();

                Log.d(Const.TAG2, "From Google:- " + mgl.getAddress());

                String formatAdd = "";
                boolean isFirst = true;
                if (city != null) {
                    formatAdd += city;
                    isFirst = false;
                }
                if (state != null) {
                    if (isFirst) {
                        formatAdd += state;
                        isFirst = false;
                    } else {
                        formatAdd += ", " + state;
                    }
                }
                if (country != null) {
                    if (isFirst) {
                        formatAdd += country;
                    } else {
                        formatAdd += ", " + country;
                    }
                }
                if (countryCode != null) {
                    backup.backupOne(Backup.CITY, city + "," + countryCode);
                    backup.backupOne(Backup.FORMAT_ADD, formatAdd);

                    //Search by City Name
                    getJankari(city + "," + countryCode, formatAdd);
                    getForcastJankari(city + "," + countryCode, formatAdd);
                    Log.d(Const.TAG2, "Being Searched :- " + city + "," + countryCode);
                    formatAddress = formatAdd;

                    //Search by Lat, Lon
//                    getJankari(lat, lon, formatAdd);
//                    getForcastJankari(lat, lon, formatAdd);
//                    Log.d(Const.TAG2, "Being Searched :- Lat-"+lat + " Lon-" +lon);
                } else {
                    backup.backupOne(Backup.CITY, city);
                    backup.backupOne(Backup.FORMAT_ADD, formatAdd);

                    //Search by City Name
                    getJankari(city, formatAdd);
                    getForcastJankari(city, formatAdd);
                    Log.d(Const.TAG2, "Being Searched :- " + city);
                    formatAddress = formatAdd;

                    //Search by Lat, Lon
//                    getJankari(lat, lon, formatAdd);
//                    getForcastJankari(lat, lon, formatAdd);
//                    Log.d(Const.TAG2, "Being Searched :- Lat-"+lat + " Lon-" +lon);
                }
            }
        });
        ld.show();
    }

    View.OnClickListener locationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            askLocation();
        }
    };


    final int CURRENT = 1;
    final int FORECAST = 2;

    private void serverErr(String errMsg, int where) {
        Log.d("SERVERERR", "IN " + where + " " + errMsg);
        notConfiguredRL.setVisibility(View.VISIBLE);
        isError = true;
        ncTv.setText("Server Error, Please try after some time.");
        ncB.setText("Retry");
        ncB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display("Retrying....");
                syncData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.my_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingMenu:
                startActivity(new Intent(this, Setting.class));
                return true;
            case R.id.aboutMenu:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.shareMenu:
                share(upperLay);
                return true;
            case R.id.weaTipMenu:
                WeatherReport wr = new WeatherReport(this, this);
                return true;
            case R.id.securityTipMenu:
                startActivity(new Intent(this, SecurityTips.class));
                return true;
            case R.id.exitMenu:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class SplashLoader extends AsyncTask<RealtimeBlurView, Integer, Intent> {
        RealtimeBlurView blurView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            splashRL.setVisibility(View.VISIBLE);
            splashMainTv.setTypeface(customFont2);
        }

        @Override
        protected Intent doInBackground(RealtimeBlurView... blurViews) {
            blurView = blurViews[0];
            int dur = SPLASH_TIME / (BLUR_UPPER - BLUR_LOWER);
            for (int i = BLUR_UPPER; i > BLUR_LOWER; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(dur);
                } catch (Exception e) {
                }
            }

            //if [is***] concept is causing errors just remove this while loop
//            while (!isFullLoaded && !isError) {
//                try {
//                    Thread.sleep(50);
//                } catch (Exception e) {
//                }
//                Log.d(Const.TAG2, "isFull , isError == "+isFullLoaded+" , "+isError);
//            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            blurView.setBlurRadius(values[0]);
        }

        @Override
        protected void onPostExecute(Intent intent) {
            super.onPostExecute(intent);
            splashRL.startAnimation(AnimationUtils.loadAnimation(ShowWeather.this, android.R.anim.fade_out));
            splashRL.setVisibility(View.GONE);
            askPermi();
        }
    }//innerEND


    private void askPermi() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .start();
    }

    String formatAddress = null;

    private void showMap() {
        if (!Setting.isShowMap(this)) {
            removeMapIV();
            return;
        }
        if (formatAddress != null) {
            //showMap

            String address = formatAddress;
            //String preUrl= "https://maps.googleapis.com/maps/api/staticmap?zoom=10&size=200x200&scale=2&markers=";
            String preUrl = "https://maps.googleapis.com/maps/api/staticmap?size=200x200&scale=2&maptype=hybrid&markers=";
            String rowUrl = "";
            try {
                rowUrl = URLEncoder.encode(address, "utf-8");
            } catch (Exception e) {
            }
            String url = preUrl + rowUrl;


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(15, 10, 15, 20);

            removeMapIV();
            mapTitleV = prepareTitleView(fullDetailsTitleV, "Map");
            upperLay.addView(mapTitleV);
            mapIV = new ImageView(this);
            mapIV.setLayoutParams(lp);
            upperLay.addView(mapIV);
            mapIV.setBackgroundResource(R.drawable.map_back_shape);
            Glide.with(this).load(url).into(mapIV);

        }
    }//showMapEND


//    private void loadAdDy() {
//        String uid = getResources().getString(R.string.below_map_banner);
//        final AdView adView= new AdView(this);
//        adView.setAdUnitId(uid);
//        adView.setAdSize(AdSize.BANNER);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//        llayout.addView(adView, params);
//
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("EB52DD64B1854FEEDCC87AC1870BD777")
//                .build();
//        adView.loadAd(adRequest);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                adView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                adView.setVisibility(View.GONE);
//            }
//        });
//
//    }


    public void share(View v) {
        display("Sharing...");
        Share share = new Share(this);
        Bitmap bitmap = share.getScreenShot(upperLay);
        File file = share.store(bitmap, "true_weather.png");
        share.shareImage(file);
    }


    public void display(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
