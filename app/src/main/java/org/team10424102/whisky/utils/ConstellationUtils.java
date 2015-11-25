package org.team10424102.whisky.utils;

import org.team10424102.whisky.models.enums.EConstellation;
import org.threeten.bp.MonthDay;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yy on 11/8/15.
 */
public class ConstellationUtils {
    private static final MonthDay[] dates = new MonthDay[]{
            MonthDay.of(1, 19),
            MonthDay.of(2, 18),
            MonthDay.of(3, 20),
            MonthDay.of(4, 10),
            MonthDay.of(5, 20),
            MonthDay.of(6, 21),
            MonthDay.of(7, 22),
            MonthDay.of(8, 22),
            MonthDay.of(9, 22),
            MonthDay.of(10, 23),
            MonthDay.of(11, 21),
            MonthDay.of(12, 21)
    };
    public static EConstellation[] constellation = new EConstellation[]{
            EConstellation.AQUARIUS,
            EConstellation.PISCES,
            EConstellation.ARIES,
            EConstellation.TAURUS,
            EConstellation.GEMINI,
            EConstellation.CANCER,
            EConstellation.LEO,
            EConstellation.VIRGO,
            EConstellation.LIBRA,
            EConstellation.SCORPIO,
            EConstellation.SAGITTARIUS,
            EConstellation.CAPRICORN
    };

    public static EConstellation getConstellation(Date date) {
        if (date == null) return null;
        int begin = 0, end = 11, mid = 5;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        MonthDay x = MonthDay.of(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        int y = 11;
        if (x.isAfter(dates[0])) {
            while (end - begin > 1) {
                if (x.isAfter(dates[mid])) {
                    end = mid;
                } else {
                    begin = mid;
                }
                mid = (begin + end) / 2;
            }
            y = begin;
        }
        return constellation[y];
    }
}
