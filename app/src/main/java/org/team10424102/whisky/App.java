package org.team10424102.whisky;

import android.app.Application;

import com.github.johnkil.print.PrintConfig;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.team10424102.whisky.components.PersistenceService;

import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import dagger.ObjectGraph;

/**
 * Created by yy on 11/4/15.
 */
public class App extends Application {
    public static final String API_STATUS = "/status";
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

    private static final String TAG = "App";
    private static App INSTANCE;

    private final Map<Class, Object> mComponents = new ConcurrentHashMap<>();
    private ObjectGraph mObjectGraph;


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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        PersistenceService persistenceService =
                (PersistenceService)getComponent(PersistenceService.class);
        persistenceService.closeDatabase();
        //SMSSDK.unregisterAllEventHandler();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public Object getComponent(Class clazz) {
        Object result = mComponents.get(clazz);
        if (result == null) throw new IllegalStateException("缺少组件 " + clazz.getCanonicalName());
        return result;
    }

    public void addComponent(Class key, Object compoment) {
        mComponents.put(key, compoment);
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    public static String getToken() {
        return "token";
    }


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                           MOB                               //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


//    @SuppressWarnings("unchecked")
//    public static void initMobSmsSdk(Context context) {
//        SMSSDK.initSDK(context, "bb474882aff3", "8b1ac2e6fa5cd29185bba8f8201d60f2");
//        EventHandler handler = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    Log.d(TAG, "获取短信验证码回调完成");
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        Log.d(TAG, "提交验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        Log.d(TAG, "获取验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                        Log.d(TAG, "返回支持发送验证码的国家列表");
//                        onCountryListGot((ArrayList<HashMap<String, Object>>) data);
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        //只使用中国的手机号码，发送的国家代码（code）是 86
//        SMSSDK.registerEventHandler(handler);
//        //SMSSDK.getSupportedCountries();
//    }
//
//    private static void onCountryListGot(ArrayList<HashMap<String, Object>> countries) {
//        // 解析国家列表
//        for (HashMap<String, Object> country : countries) {
//            String code = (String) country.get("zone");
//            String rule = (String) country.get("rule");
//            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
//                continue;
//            }
//
//            if (countryRules == null) {
//                countryRules = new HashMap<String, String>();
//            }
//            countryRules.put(code, rule);
//        }
//
//        for (HashMap.Entry<String, String> entry : countryRules.entrySet()) {
//            Log.d(TAG, entry.getKey() + ": " + entry.getValue());
//        }
//    }

}
