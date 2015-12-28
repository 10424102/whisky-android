package org.team10424102.whisky;

import android.app.Application;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.johnkil.print.PrintConfig;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.components.ApiAuthInterceptor;
import org.team10424102.whisky.components.ApiService;
import org.team10424102.whisky.components.AuthService;
import org.team10424102.whisky.components.PersistenceService;
import org.team10424102.whisky.components.ErrorManager;
import org.team10424102.whisky.components.GameManager;
import org.team10424102.whisky.components.LocalizationInterceptor;
import org.team10424102.whisky.components.LoggingInterceptor;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.enums.AndroidStringResourceProvided;
import org.team10424102.whisky.models.extensions.PostExtensionData;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtension;
import org.team10424102.whisky.models.extensions.image.ImagePostExtension;
import org.team10424102.whisky.models.extensions.poll.PollPostExtension;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;
import org.team10424102.whisky.utils.DimensionUtils;
import org.team10424102.whisky.utils.EnumUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by yy on 11/4/15.
 */
public class App extends Application {
    public static final String API_STATUS = "/status";
    public static final String API_USER = "/api/users";
    public static final String API_IMAGE = "/api/images";
    public static final String API_POST = "/api/posts";
    public static final String API_ACTIVITY = "/api/activities";
    public static final String API_GAME = "/api/games";

    public static final String PREF_SERVER_ADDRESS = "server_address";
    public static final String PREF_LOG_FILE_NAME_PREFIX = "log_file_name_prefix";
    public static final String PREF_LOG_FILE_LOCATION = "log_file_location";
    public static final String PREF_LOG_SWITCH = "log_switch";
    public static final String DEFAULT_SERVER_ADDRESS = "oplogobit.eicp.net:29008";
    public static final String DEFAULT_LOG_FILE_NAME_PREFIX = "WALog_";
    public static final String DEFAULT_LOG_FILE_LOCATION = "/Whisky";
    public static final String DEFAULT_LOG_SWITCH = "off";

    public static final int ERR_RENEW_TOKEN = 1;
    public static final int ERR_SERVER_MAINTENANCE = 2;
    public static final int ERR_NETWORK = 3;
    public static final int ERR_INIT_RETROFIT = 4;
    public static final int ERR_INVOKING_API = 5;

    public static final String PREF_NAME = "org.team10424102.whisky";

    private static final String TAG = "App";
    private final static Profile profile = new Profile();
    private static Context context;
    private static ApiService apiService;
    private static Picasso picasso;
    private static ErrorManager errorManager;
    private static AuthService authManager;
    private static PersistenceService persistenceService;
    private static PostExtensionManager postExtensionManager;
    private static ObjectMapper objectMapper;
    private static OkHttpClient httpClient;
    private static String host;
    private static HashMap<String, String> countryRules;


    private static App INSTANCE;

    private GameManager mGameManager;
    private Map<Class, Object> mComponents = new ConcurrentHashMap<>();

    public App() {
        INSTANCE = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

        // Icon font
        PrintConfig.initDefault(getAssets(), "ionicons.ttf");

        // JSR-310
        AndroidThreeTen.init(this);

        context = getApplicationContext();

        errorManager = new ErrorManager();
        persistenceService = new PersistenceService(context);
        authManager = new AuthService(persistenceService);

        // OkHttpClient
        httpClient = new OkHttpClient();
        httpClient.interceptors().add(new ApiAuthInterceptor());
        httpClient.interceptors().add(new LocalizationInterceptor(context));
        httpClient.interceptors().add(new LoggingInterceptor());
        httpClient.setConnectTimeout(1, TimeUnit.SECONDS);
        httpClient.setReadTimeout(1, TimeUnit.SECONDS);

        // Picasso
        picasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(httpClient)).build();


//        SharedPreferences pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        String serverAddress = pref.getString(PREF_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
//        initRetrofit(serverAddress);

        DimensionUtils.init(context);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        persistenceService.closeDatabase();
        //SMSSDK.unregisterAllEventHandler();
    }


    public void initRetrofit(String serverAddress) {
        host = "http://" + serverAddress;

        // ObjectMapper
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(httpClient)
                .build();

        apiService = retrofit.create(ApiService.class);

        mGameManager = new GameManager(apiService);

        SimpleModule module = new SimpleModule();
        //module.addDeserializer(Profile.class, new ProfileDeserializer());
        module.addSerializer(LazyImage.class, new LazyImageSerializer());
        module.addDeserializer(LazyImage.class, new LazyImageDeserializer());
        module.addDeserializer(PostExtensionData.class, new PostExtensionDeserializer());
        objectMapper.registerModule(module);

        postExtensionManager = new PostExtensionManager(mGameManager);
        postExtensionManager.registerPostExtension(new Dota2PostExtension());
        postExtensionManager.registerPostExtension(new ImagePostExtension());
        postExtensionManager.registerPostExtension(new PollPostExtension());
    }

    public void changeServerAddress(String serverAddress) {
        initRetrofit(serverAddress);
    }

    public GameManager getGameManager() {
        return mGameManager;
    }


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                         BINDING                             //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @BindingAdapter({"bind:lazyImage"})
    public static void loadLazyImage(ImageView view, LazyImage image) {
        if (image == null) return;
        picasso.load(getLazyImageUrl(image)).error(R.drawable.dummy_profile_bg).into(view);
    }

    public static String getLazyImageUrl(LazyImage image) {
        return host + API_IMAGE + "?q=" + image.getAccessToken();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        picasso.load(imageUrl).into(view);
    }

    @BindingAdapter({"bind:drawableResId"})
    public static void loadImage(ImageView view, int resId) {
        if (resId == 0) return;
        picasso.load(resId).into(view);
    }

    @BindingAdapter({"bind:enum"})
    public static void loadEnum(TextView view, AndroidStringResourceProvided e) {
        if (e == null) return;
        view.setText(e.getStringResId());
    }

    @BindingAdapter({"bind:enum"})
    public static void loadEnumToSpinner(Spinner spinner, AndroidStringResourceProvided e) {
        List<String> data = EnumUtils.getStringList(spinner.getContext(), ((Enum) e).getClass());
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter({"bind:date", "bind:dateFormat"})
    public static void dateToString(TextView view, Date date, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        view.setText(dt.format(date));
    }


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                           MOB                               //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


    @SuppressWarnings("unchecked")
    public static void initMobSmsSdk(Context context) {
        SMSSDK.initSDK(context, "bb474882aff3", "8b1ac2e6fa5cd29185bba8f8201d60f2");
        EventHandler handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.d(TAG, "获取短信验证码回调完成");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.d(TAG, "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.d(TAG, "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Log.d(TAG, "返回支持发送验证码的国家列表");
                        onCountryListGot((ArrayList<HashMap<String, Object>>) data);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        //只使用中国的手机号码，发送的国家代码（code）是 86
        SMSSDK.registerEventHandler(handler);
        //SMSSDK.getSupportedCountries();
    }

    private static void onCountryListGot(ArrayList<HashMap<String, Object>> countries) {
        // 解析国家列表
        for (HashMap<String, Object> country : countries) {
            String code = (String) country.get("zone");
            String rule = (String) country.get("rule");
            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
                continue;
            }

            if (countryRules == null) {
                countryRules = new HashMap<String, String>();
            }
            countryRules.put(code, rule);
        }

        for (HashMap.Entry<String, String> entry : countryRules.entrySet()) {
            Log.d(TAG, entry.getKey() + ": " + entry.getValue());
        }
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                          OTHER                              //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @NonNull
    public static ApiService api() {
        return apiService;
    }

    public static void handleError(int errorCode) {

    }

    public static Profile getProfile() {
        return profile;
    }

    public static PersistenceService getPersistenceService() {
        return persistenceService;
    }

    public static Picasso getPicasso() {
        return picasso;
    }

    public static PostExtensionManager getPostExtensionManager() {
        return postExtensionManager;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public Object getComponent(Class clazz) {
        return mComponents.get(clazz);
    }

}
