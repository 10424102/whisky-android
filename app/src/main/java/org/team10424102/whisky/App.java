package org.team10424102.whisky;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.johnkil.print.PrintConfig;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.CoreModule;
import org.team10424102.whisky.components.LocalizationInterceptor;
import org.team10424102.whisky.components.auth.ApiAuthenticationInterceptor;
import org.team10424102.whisky.models.BlackServer;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtensionHandler;
import org.team10424102.whisky.models.extensions.image.ImagePostExtensionHandler;
import org.team10424102.whisky.models.extensions.poll.PollPostExtensionHandler;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import dagger.ObjectGraph;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.JacksonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by yy on 11/4/15.
 */
public class App extends Application {
    public static final String API_PREFIX = "/api";

    public static final String PREF_SERVER_ADDRESS = "server_address";
    public static final String PREF_LOG_FILE_NAME_PREFIX = "log_file_name_prefix";
    public static final String PREF_LOG_FILE_LOCATION = "log_file_location";
    public static final String PREF_LOG_SWITCH = "log_switch";
    public static final String DEFAULT_SERVER_ADDRESS = "192.168.1.100:8080";
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

    public static BlackServerApi api;
    public static Picasso picasso;
    public static Context context;
    public static PostExtensionManager postExtensionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.d("Timber plant DebugTree");
            Timber.plant(new Timber.DebugTree());
        }
        context = getApplicationContext();
        init();
    }

    public void init() {
        Timber.d("开始初始化");

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        PrintConfig.initDefault(getAssets(), "ionicons.ttf");// Icon font
        AndroidThreeTen.init(this);// JSR-310
        DimensionUtils.init(context);

        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client = client.newBuilder()
                .readTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(1,TimeUnit.SECONDS)
                .addInterceptor(new ApiAuthenticationInterceptor(context))
                .addInterceptor(new LocalizationInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .build();
        picasso = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        PostExtensionDeserializer deserializer2 = new PostExtensionDeserializer();
        module.addDeserializer(PostExtension.class, deserializer2);
        Retrofit retrofit = new Retrofit.Builder()
                // .baseUrl("https://bs.projw.org")
                // .baseUrl("http://192.168.2.108:8080")
                .baseUrl("http://127.0.0.1:8080")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(BlackServerApi.class);

        postExtensionManager = new PostExtensionManager();
        postExtensionManager.registerPostExtensionHandler(new Dota2PostExtensionHandler());
        postExtensionManager.registerPostExtensionHandler(new ImagePostExtensionHandler());
        postExtensionManager.registerPostExtensionHandler(new PollPostExtensionHandler());

        Timber.d("初始化完成");
    }
}