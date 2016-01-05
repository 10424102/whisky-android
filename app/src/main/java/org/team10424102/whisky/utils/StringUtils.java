package org.team10424102.whisky.utils;

import org.team10424102.whisky.App;

public class StringUtils {

    public static String getString(int resId) {
        return App.getInstance().getString(resId);
    }

}
