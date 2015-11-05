package org.team10424102.whisky;

import android.app.Application;

import com.github.johnkil.print.PrintConfig;

import java.util.TimeZone;

/**
 * Created by yy on 11/4/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        PrintConfig.initDefault(getAssets(), "fonts/material-icon-font.ttf");
        Global.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Global.terminate();
    }
}
