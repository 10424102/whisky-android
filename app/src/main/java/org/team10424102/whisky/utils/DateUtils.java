package org.team10424102.whisky.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yy on 11/14/15.
 */
public class DateUtils {
    public static String format(String format, Date date) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }
}
