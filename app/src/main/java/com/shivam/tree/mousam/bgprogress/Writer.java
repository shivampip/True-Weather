package com.shivam.tree.mousam.bgprogress;

import android.content.Context;

import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.shiv.WeahterDay;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sittu Agrawal on 17-01-2017.
 */

public class Writer {

    public static final int COLD_NORMAL = 13;
    public static final int COLD_MEDIUM = 4;
    public static final int COLD_HIGH = 1;
    public static final int HOT_NORMAL = 33;
    public static final int HOT_MEDIUM = 39;
    public static final int HOT_HIGH = 45;


    public static String prepareDetails(Context context, Condition con, WeahterDay day) {
        String details = "";

//        details = getWelcomeNote()+"...\n";
//        details+= "Todays weather will be something like this\n";
//        details+= desc;

        details += getWelcomeNote() + "\n";
        details += getBody(con.getMain()) + " " + getTimeText(con.getDt()) + " ";
        if (getTempLine(context, day) != null) {
            details += getTempLine(context, day) + "\n";
        }
        details += getEffect(con.getMain());

        return details;
    }

    public static String getTempLine(Context context, WeahterDay day) {
        double max = day.getTempMax();
        double min = day.getTempMin();
        String text = null;
        if (max > HOT_HIGH) {
            text = context.getString(R.string.hot_high);
        } else if (max > HOT_MEDIUM) {
            text = context.getString(R.string.hot_medium);
        } else if (max > HOT_NORMAL) {
            text = context.getString(R.string.hot_normal);
        } else if (min < COLD_HIGH) {
            text = context.getString(R.string.cold_high);
        } else if (min < COLD_MEDIUM) {
            text = context.getString(R.string.cold_medium);
        } else if (min < COLD_NORMAL) {
            text = context.getString(R.string.cold_normal);
        }
        return text;
    }

    public static String getWelcomeNote() {
        String note = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hoursOfDay = cal.get(Calendar.HOUR_OF_DAY);
        switch (hoursOfDay) {
            case 0:
            case 1:
            case 2:
            case 3:
                note = "Good Night";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                note = "Good Morning";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                note = "Good Afternoon";
                break;
            case 17:
            case 18:
                note = "Good Evening";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                note = "Good Night";
                break;
            default:
                note = "Welcome";
                break;
        }

        return note;
    }

    public static String getTimeName(Date dt) {
        String note = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int hoursOfDay = cal.get(Calendar.HOUR_OF_DAY);
        switch (hoursOfDay) {
            case 0:
            case 1:
            case 2:
            case 3:
                note = "night";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                note = "morning";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                note = "afternoon";
                break;
            case 17:
            case 18:
                note = "evening";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                note = "night";
                break;
            default:
                note = "";
                break;
        }
        return note;
    }

    public static String getTimeText(Date dt) {
        String note = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int hoursOfDay = cal.get(Calendar.HOUR_OF_DAY);
        switch (hoursOfDay) {
            case 0:
            case 1:
            case 2:
            case 3:
                note = "at night";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                note = "in the morning";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                note = "in the afternoon";
                break;
            case 17:
            case 18:
                note = "in the evening";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                note = "at night";
                break;
            default:
                note = "";
                break;
        }
        return note + ".";
    }

    public static String getEffect(String mainDesc) {
        String text = "";
        switch (mainDesc) {
            case "Clouds":
                text = "Have some snakes in cloudy weather.";
                break;
            case "Rain":
                text = "make umbrala your friend. don't go outside without it.";
                break;
            case "Clear":
                text = "Just Enjoy clear weather.";
                break;
            case "Snow":
                text = "get Ready for a snow fight.";
                break;
            default:
                text = "Case is " + mainDesc;
                break;
        }
        return text;
    }

    public static String getBody(String mainDesc) {
        String text = "";
        switch (mainDesc) {
            case "Clouds":
                text = "It will be cloudy weather";
                break;
            case "Rain":
                text = "It may rain";
                break;
            case "Clear":
                text = "Weather will be clear";
                break;
            case "Snow":
                text = "It will snow fall";
                break;
            default:
                text = "Case is " + mainDesc;
                break;
        }
        return text;
    }


}//classEND
