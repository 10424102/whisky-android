package org.team10424102.whisky;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.johnkil.print.PrintConfig;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.team10424102.whisky.components.CoreModule;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionHandler;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtensionHandler;
import org.team10424102.whisky.models.extensions.image.ImagePostExtensionHandler;
import org.team10424102.whisky.models.extensions.poll.PollPostExtensionHandler;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.TimeZone;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by yy on 11/4/15.
 */
public class App extends Application {
    public static final String TAG = "App";
    public static final String API_PREFIX = "/api";

    public static final String PREF_SERVER_ADDRESS = "server_address";
    public static final String PREF_LOG_FILE_NAME_PREFIX = "log_file_name_prefix";
    public static final String PREF_LOG_FILE_LOCATION = "log_file_location";
    public static final String PREF_LOG_SWITCH = "log_switch";
    public static final String DEFAULT_SERVER_ADDRESS = "oplogobit.eicp.net:29008";
    public static final String DEFAULT_LOG_FILE_NAME_PREFIX = "WALog_";
    public static final String DEFAULT_LOG_FILE_LOCATION = "/Whisky";
    public static final String DEFAULT_LOG_SWITCH = "off";
    public static final int DEFAULT_NO_IMAGE = R.drawable.no_image;
    public static final int DEFAULT_AVATAR = R.drawable.no_image;
    public static final int DEFAULT_BACKGROUND = R.drawable.no_image;
    public static final int DEFAULT_COVER = R.drawable.no_image;

    public static final int ERR_RENEW_TOKEN = 1;
    public static final int ERR_SERVER_MAINTENANCE = 2;
    public static final int ERR_NETWORK = 3;
    public static final int ERR_INIT_RETROFIT = 4;
    public static final int ERR_INVOKING_API = 5;

    public static final String PREF_NAME = "org.team10424102.whisky";

    private static App INSTANCE;

    private ObjectGraph mObjectGraph;

    public App() {
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        Timber.i("App onCreate()");
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.d("Timber plant DebugTree");
            Timber.plant(new Timber.DebugTree());
        }
    }

    public void init() {
        Timber.i("App init()");

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        // Icon font
        PrintConfig.initDefault(getAssets(), "ionicons.ttf");
        // JSR-310
        AndroidThreeTen.init(this);

        DimensionUtils.init(getApplicationContext());

        mObjectGraph = ObjectGraph.create(new CoreModule(getApplicationContext()));

    }

    public static App getInstance() {
        return INSTANCE;
    }

    public String getHost() {
        SharedPreferences prefs = getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
        String host = "http://" + prefs.getString(App.PREF_SERVER_ADDRESS, App.DEFAULT_SERVER_ADDRESS);
        return host;
    }

    /**
     * hide this method call, because it will generate exceptions when we using dagger
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T getComponent(Class<T> clazz) {
        return mObjectGraph.get(clazz);
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}