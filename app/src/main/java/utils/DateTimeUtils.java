package utils;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class DateTimeUtils {

    public static String getDateTime(String str) {

        String standardStr = str.substring(0, 19);
        String[] datetimes = standardStr.split("T");
        String result = datetimes[0] + " " + datetimes[1];
        return result;
    }
}
