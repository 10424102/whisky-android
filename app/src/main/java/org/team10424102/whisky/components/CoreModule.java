package org.team10424102.whisky.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.components.api.UserApi;
import org.team10424102.whisky.components.auth.ApiAuthInterceptor;
import org.team10424102.whisky.controllers.MainActivity;
import org.team10424102.whisky.controllers.VcodeActivity;
import org.team10424102.whisky.controllers.WelcomeActivity;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.extensions.PostExtensionData;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtension;
import org.team10424102.whisky.models.extensions.image.ImagePostExtension;
import org.team10424102.whisky.models.extensions.poll.PollPostExtension;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Module(
        injects = {
                WelcomeActivity.class,
                VcodeActivity.class,
                MainActivity.class
        }
)
public class CoreModule {

    private Context mContext;

    public CoreModule(Context context) {
        mContext = context;
    }

    @Provides @Singleton ErrorManager provideErrorManager() {
        return new ErrorManager();
    }

    @Provides @Singleton PersistenceService providePersistenceService() {
        return new PersistenceService(mContext);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ApiAuthInterceptor(mContext));
        client.interceptors().add(new LocalizationInterceptor(mContext));
        client.interceptors().add(new LoggingInterceptor());
        client.setConnectTimeout(1, TimeUnit.SECONDS);
        client.setReadTimeout(1, TimeUnit.SECONDS);
        return client;
    }

    @Provides @Singleton Picasso providePicasso(OkHttpClient client) {
        Picasso picasso = new Picasso
                .Builder(mContext)
                .downloader(new OkHttpDownloader(client))
                .build();
        return picasso;
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addSerializer(LazyImage.class, new LazyImageSerializer());
        module.addDeserializer(LazyImage.class, new LazyImageDeserializer());
        module.addDeserializer(PostExtensionData.class, new PostExtensionDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }

    @Provides @Singleton Retrofit provideRetrofit(ObjectMapper objectMapper, OkHttpClient client) {
        SharedPreferences prefs = mContext.getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
        String host = "http://" + prefs.getString(App.PREF_SERVER_ADDRESS, App.DEFAULT_SERVER_ADDRESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        return retrofit;
    }

    @Provides @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides @Singleton
    UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

    @Provides @Singleton GameManager provideGameManager(ApiService apiService) {
        return new GameManager(apiService);
    }

    @Provides @Singleton PostExtensionManager providePostExtensionManager(GameManager gameManager) {
        PostExtensionManager postExtensionManager = new PostExtensionManager(gameManager);
        postExtensionManager.registerPostExtension(new Dota2PostExtension());
        postExtensionManager.registerPostExtension(new ImagePostExtension());
        postExtensionManager.registerPostExtension(new PollPostExtension());
        return postExtensionManager;
    }
}
