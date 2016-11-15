package com.shg.manhourapp2.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class DateTimeUtils {

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getDateTime(String str) {

        if (str != null && str.length() != 0) {
            String standardStr = str.substring(0, 19);
            String[] datetimes = standardStr.split("T");
            String result = datetimes[0] + " " + datetimes[1];
            return result;
        }
        return null;
    }

    public static String getDateTime(String str, String position) {

        if (str != null && str.length() != 0) {
            String standardStr = str.substring(0, 19);
            String[] datetimes = standardStr.split("T");
            String result = datetimes[0] + " " + datetimes[1];
            return result;
        }
        return position;
    }

    public static String getStartTime(String endTime, double actualHours, String position) {

        String startTime = null;

        if (endTime != null && endTime.length() != 0) {

//            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

            try {
                Date endTime_date = format.parse(endTime);
                long endTime_long = endTime_date.getTime();
                long startTime_long = endTime_long - (long) (actualHours * 3600 * 1000);
//                Log.d("MyLog", endTime_long + "|||" + startTime_long);
                Date startTime_date = new Date(startTime_long);
//                Log.d("MyLog", endTime_date + "|||" + startTime_date);
                startTime = format.format(startTime_date);
            } catch (ParseException e) {
                e.printStackTrace();
                return position;
            }

            return startTime;
        }

        return position;
    }


    public static double computeManHourActual(String startTime, String endTime) {

        double manHours = 0.00;

        DecimalFormat df = new DecimalFormat("######0.00");


        try {
            long s_Time = format.parse(startTime).getTime();
            long e_Time = format.parse(endTime).getTime();

            manHours = (double) (e_Time - s_Time) / (3600 * 1000);
            manHours = Double.parseDouble(df.format(manHours));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return manHours;
    }
}
