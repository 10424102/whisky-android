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

    public static String toHumanReadableString(Date date) {
        Date now = new Date();
        long d = (now.getTime() - date.getTime()) / 1000;
        if (d < 60) return "刚刚";
        else if (d < 60 * 60) return d / 60 + " 分钟前";
        else if (d < 60 * 60 * 24) return d / 60 / 60 + " 小时前";

        return format("yyyy-MM-dd HH:mm", date);
    }
}
