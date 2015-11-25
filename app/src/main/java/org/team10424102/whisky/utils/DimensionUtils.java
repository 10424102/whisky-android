package org.team10424102.whisky.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by yy on 11/6/15.
 */
public class DimensionUtils {
    private static Resources res;
    private static float factor;
    private final float AVATAR_USERNAME_GAP = 16;
    private final float APPBAR_HEIGHT = 102;
    private final float DEFAULT_TOOLBAR_HEIGHT = 56;

    public static void init(Context context) {
        res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        factor = metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }

    public static int dp2px(float dp) {
        return Math.round(dp * factor);
    }

    public static float px2dp(int px) {
        return Math.round(px / factor);
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
