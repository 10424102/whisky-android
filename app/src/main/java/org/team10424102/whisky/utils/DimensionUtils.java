package org.team10424102.whisky.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by yy on 11/6/15.
 */
public class DimensionUtils {
    private final float AVATAR_USERNAME_GAP = 16;
    private final float APPBAR_HEIGHT = 102;
    private final float DEFAULT_TOOLBAR_HEIGHT = 56;

    public static float dp2px(float dp, Resources res) {
        //return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
        DisplayMetrics metrics = res.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    public static float px2dp(float px, Resources res) {
        DisplayMetrics metrics = res.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static int getStatusBarHeight(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
