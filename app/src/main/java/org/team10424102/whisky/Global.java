package org.team10424102.whisky;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.components.ApiService;
import org.team10424102.whisky.components.AuthenticationManager;
import org.team10424102.whisky.components.ErrorManager;
import org.team10424102.whisky.components.RestInterceptor;
import org.team10424102.whisky.daos.DataManager;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by yy on 11/7/15.
 */
public class Global {
    public static final String TAG = "Global";
    public static final int SERVER_STATUS_OK = 0;          // 服务器运行正常
    public static final int SERVER_STATUS_MAINTENANCE = 1; // 服务器维护
    public static AuthenticationManager authenticationManager;
    public static ApiService apiService;
    public static DataManager dataManager;
    public static ErrorManager errorManager;
    public static Context context;
    public static String currentUserPhone = "";
    public static String currentUserToken = "";
    public static Picasso picasso;
    public static String host;
    private static Profile profile = new Profile();
    private static HashMap<String, String> countryRules;

    public static boolean initRetrofit(Context context, String serverAddress) {
        host = "http://" + serverAddress;
        try {
            RestInterceptor interceptor = new RestInterceptor();

            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(interceptor);

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            //module.addDeserializer(Profile.class, new ProfileDeserializer());
            module.addSerializer(LazyImage.class, new LazyImageSerializer());
            module.addDeserializer(LazyImage.class, new LazyImageDeserializer());
            objectMapper.registerModule(module);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + serverAddress)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .client(client)
                    .build();
            apiService = retrofit.create(ApiService.class);

            picasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(client)).build();

            return true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            errorManager.handleError(context, ErrorManager.ERR_INIT_RETROFIT);
        }
        return false;
    }

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


    public static void init(Application app) {
        // Save Context
        context = app.getApplicationContext();
        // Error Manager
        errorManager = new ErrorManager();
        // JSR-310 DateTime
        AndroidThreeTen.init(app);
        // Image cache
        //ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(app).build();
        //ImageLoader.getInstance().init(config);
        // Authentication Manager
        authenticationManager = new AuthenticationManager();
        // Data Manager
        dataManager = new DataManager(app.getApplicationContext());
        // Mob SMSSDK
        //initMobSmsSdk();
    }

    public static void terminate() {
        dataManager.closeDatabase();
        SMSSDK.unregisterAllEventHandler();
    }

    public static Profile getProfile() {
        return profile;
    }

    public static void setProfile(Profile profile) {
        Global.profile.copyAndUpdateUI(profile);
    }
}
