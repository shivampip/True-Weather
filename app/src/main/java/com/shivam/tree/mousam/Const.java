package com.shivam.tree.mousam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Sittu Agrawal on 07-01-2017.
 */

public class Const {

    final public static String TAG = "SSHHIIVVAAMMAAGG";
    final public static String TAG2 = "SSHHIIVVAAMMAAGGRR";
    final public static String DEGREE = "°";
    final public static String ARROW_UP = "↑";
    final public static String ARROW_DOWN = "↓";
    final public static String NO_BREAK = "\u0020";

    final public static String SHOULD_SHOW_MAP = "shouldShowMap";
    final public static String SHOULD_SHOW_NOTI= "shouldShowNoti";


    final public static String API_KEY = "xxx";

    final public static String DIALOG_CODE = "dialogCode";
    final public static int DC_TIP = 111;
    final public static int DC_WISHES = 14322;


    final public static int TF_JUNGE_REGULAR = 1;
    final public static int TF_NERKSHIRE_SWASH = 2;


    private final static String key1 = "xxx";
    private final static String key2 = "xxx";
    private final static String key3 = "xxx";
    private final static String key4 = "xxx";
    private final static String key5 = "xxx";

    //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

    public static String getRanAPIKey() {
        String key[] = {key1, key2, key3, key4, key5};
        Random rand = new Random();
        int n = rand.nextInt(4) + 0;
        //50 is the maximum and the 1 is our minimum
        return key[n];
    }


    public static Typeface getCustomTypeface(Context context, int type) {
        switch (type) {
            case 1:
                return Typeface.createFromAsset(context.getAssets(), "fonts/acme_regular.ttf");
            case 2:
                return Typeface.createFromAsset(context.getAssets(), "fonts/nerkshire_swash.ttf");
            default:
                return null;
        }
    }


    public static int getIconRes(String name) {
        int res = 0;

        if (name == null) {
            return res;
        }

        switch (name) {
            case "01d":
                res = R.drawable.shiv01d;
                break;
            case "01n":
                res = R.drawable.shiv01n;
                break;
            case "02d":
                res = R.drawable.shiv02d;
                break;
            case "02n":
                res = R.drawable.shiv02n;
                break;
            case "03d":
                res = R.drawable.shiv03d;
                break;
            case "03n":
                res = R.drawable.shiv03n;
                break;
            case "04d":
                res = R.drawable.shiv04d;
                break;
            case "04n":
                res = R.drawable.shiv04n;
                break;
            case "09d":
                res = R.drawable.shiv09d;
                break;
            case "09n":
                res = R.drawable.shiv09n;
                break;
            case "10d":
                res = R.drawable.shiv10d;
                break;
            case "10n":
                res = R.drawable.shiv10n;
                break;
            case "11d":
                res = R.drawable.shiv11d;
                break;
            case "11n":
                res = R.drawable.shiv11n;
                break;
            case "13d":
                res = R.drawable.shiv13d;
                break;
            case "13n":
                res = R.drawable.shiv13n;
                break;
            case "50d":
                res = R.drawable.shiv50d;
                break;
            case "50n":
                res = R.drawable.shiv50n;
                break;
        }
        return res;
    }//getResIconEND


    public static String format(double value, int till) {
        return String.format("%." + till + "f", value);
    }

    public static int getAIColor() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int cHour = cal.get(Calendar.HOUR_OF_DAY);

        int color = Color.argb(119, 94, 94, 94);

        switch (cHour) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                color = Color.argb(184, 1, 7, 61);
                break;
        }

        return color;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getApiKey() {
        return API_KEY;
    }


    public static String getDescription(int id) {
        String desc = null;

        switch (id) {
            case 200:
                desc = "thunderstorm with light rain";
                break;
            case 201:
                desc = "thunderstorm with rain";
                break;
            case 202:
                desc = "thunderstorm with heavy rain";
                break;
            case 210:
                desc = "light thunderstorm";
                break;
            case 211:
                desc = "thunderstorm";
                break;
            case 212:
                desc = "heavy thunderstorm";
                break;
            case 221:
                desc = "ragged thunderstorm";
                break;
            case 230:
                desc = "thunderstorm with light drizzle";
                break;
            case 231:
                desc = "thunderstorm with drizzle";
                break;
            case 232:
                desc = "thunderstorm with heavy drizzle";
                break;


            case 300:
                desc = "      light intensity drizzle  ";
                break;


            case 301:
                desc = "     drizzle   ";
                break;


            case 302:
                desc = "    heavy intensity drizzle    ";
                break;


            case 310:
                desc = "    light intensity drizzle rain    ";
                break;


            case 311:
                desc = "     drizzle rain   ";
                break;


            case 312:
                desc = "     heavy intensity drizzle rain   ";
                break;


            case 313:
                desc = "    shower rain and drizzle     ";
                break;


            case 314:
                desc = "     heavy shower rain and drizzle   ";
                break;


            case 321:
                desc = "      shower drizzle  ";
                break;


            case 500:
                desc = "   light rain     ";
                break;


            case 501:
                desc = "      moderate rain  ";
                break;


            case 502:
                desc = "    heavy intensity rain    ";
                break;


            case 503:
                desc = "    very heavy rain    ";
                break;


            case 504:
                desc = "    extreme rain    ";
                break;


            case 511:
                desc = "freezing rain   ";
                break;


            case 520:
                desc = "light intensity shower rain";
                break;


            case 521:
                desc = "       shower rain  ";
                break;

            case 522:
                desc = "    heavy intensity shower rain     ";
                break;

            case 531:
                desc = "    ragged shower rain     ";
                break;


            case 600:
                desc = "    light snow     ";
                break;

            case 601:
                desc = "     snow    ";
                break;

            case 602:
                desc = "     heavy snow      ";
                break;

            case 611:
                desc = "       sleet  ";
                break;

            case 612:
                desc = "     shower sleet   ";
                break;

            case 615:
                desc = "       light rain and snow  ";
                break;

            case 616:
                desc = "      rain and snow   ";
                break;

            case 620:
                desc = "      light shower snow   ";
                break;

            case 621:
                desc = "       shower snow  ";
                break;

            case 622:
                desc = "      heavy shower snow   ";
                break;


            case 701:
                desc = "     mist    ";
                break;

            case 711:
                desc = "     smoke    ";
                break;

            case 721:
                desc = "     haze    ";
                break;
            case 731:
                desc = "       sand, dust whirls  ";
                break;

            case 741:
                desc = "      fog ";
                break;

            case 751:
                desc = "      sand   ";
                break;
            case 761:
                desc = "     dust    ";
                break;

            case 762:
                desc = "       volcanic ash  ";
                break;

            case 771:
                desc = "       squalls  ";
                break;
            case 781:
                desc = "      tornado   ";
                break;

            case 800:
                desc = "clear sky";
                break;


            case 801:
                desc = "     few clouds    ";
                break;
            case 802:
                desc = "     scattered clouds    ";
                break;

            case 803:
                desc = "      broken clouds   ";
                break;

            case 804:
                desc = "      overcast clouds   ";
                break;


            case 900:
                desc = "     tornado    ";
                break;

            case 901:
                desc = "     tropical storm    ";
                break;

            case 902:
                desc = "    hurricane ";
                break;


            case 903:
                desc = "   cold  ";
                break;

            case 904:
                desc = "  hot   ";
                break;
            case 905:
                desc = "  windy   ";
                break;
            case 906:
                desc = "  hail   ";
                break;
            case 951:
                desc = " calm    ";
                break;
            case 952:
                desc = "  light breeze   ";
                break;
            case 953:
                desc = " gentle breeze    ";
                break;
            case 954:
                desc = "  moderate breeze   ";
                break;

            case 955:
                desc = "  fresh breeze   ";
                break;
            case 956:
                desc = "  strong breeze   ";
                break;
            case 957:
                desc = "   high wind, near gale  ";
                break;
            case 958:
                desc = " gale    ";
                break;
            case 959:
                desc = "  severe gale   ";
                break;
            case 960:
                desc = " storm    ";
                break;
            case 961:
                desc = "violent storm     ";
                break;
            case 962:
                desc = "  hurricane   ";
                break;

        }

        return desc.trim();
    }

}//classEND
