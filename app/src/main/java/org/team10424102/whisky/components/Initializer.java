package org.team10424102.whisky.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.components.auth.ApiAuthInterceptor;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.extensions.PostExtensionData;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtension;
import org.team10424102.whisky.models.extensions.image.ImagePostExtension;
import org.team10424102.whisky.models.extensions.poll.PollPostExtension;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Deprecated
public class Initializer {
    public static final String TAG = "Initializer";

    private final Context mContext;
    private final CountDownLatch mAccountServiceInitialized = new CountDownLatch(1);


    public Initializer(Context context) {
        mContext = context;
    }

    public void init() {
        App app = ((App) mContext.getApplicationContext());

        app.addComponent(ErrorManager.class, new ErrorManager());
        Log.d(TAG, "[添加组件] ErrorManager");

        app.addComponent(PersistenceService.class, new PersistenceService(mContext));
        Log.d(TAG, "[添加组件] PersistenceService");

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ApiAuthInterceptor(mContext));
        client.interceptors().add(new LocalizationInterceptor(mContext));
        client.interceptors().add(new LoggingInterceptor());
        client.setConnectTimeout(1, TimeUnit.SECONDS);
        client.setReadTimeout(1, TimeUnit.SECONDS);

        Picasso picasso = new Picasso
                .Builder(mContext)
                .downloader(new OkHttpDownloader(client))
                .build();
        app.addComponent(Picasso.class, picasso);
        Log.d(TAG, "[添加组件] Picasso");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SharedPreferences prefs = mContext.getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
        String host = "http://" + prefs.getString(App.PREF_SERVER_ADDRESS, App.DEFAULT_SERVER_ADDRESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        app.addComponent(Retrofit.class, retrofit);
        Log.d(TAG, "[添加组件] Retrofit");

        ApiService apiService = retrofit.create(ApiService.class);
        app.addComponent(ApiService.class, apiService);
        Log.d(TAG, "[添加组件] ApiService");

        GameManager gameManager = new GameManager(apiService);
        app.addComponent(GameManager.class, gameManager);
        Log.d(TAG, "[添加组件] GameManager");

        SimpleModule module = new SimpleModule();
        module.addSerializer(LazyImage.class, new LazyImageSerializer());
        module.addDeserializer(LazyImage.class, new LazyImageDeserializer());
        module.addDeserializer(PostExtensionData.class, new PostExtensionDeserializer());
        objectMapper.registerModule(module);

        PostExtensionManager postExtensionManager = new PostExtensionManager(gameManager);
        postExtensionManager.registerPostExtension(new Dota2PostExtension());
        postExtensionManager.registerPostExtension(new ImagePostExtension());
        postExtensionManager.registerPostExtension(new PollPostExtension());
        app.addComponent(PostExtensionManager.class, postExtensionManager);
        Log.d(TAG, "[添加组件] PostExtensionManager");

        DimensionUtils.init(mContext);
        Log.d(TAG, "[初始化] DimensionUtils");
    }
}
