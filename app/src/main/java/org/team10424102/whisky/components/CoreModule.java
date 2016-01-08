package org.team10424102.whisky.components;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.auth.AccountRepo;
import org.team10424102.whisky.components.auth.AccountRepoImpl;
import org.team10424102.whisky.components.auth.AccountService;
import org.team10424102.whisky.components.auth.ApiAuthInterceptor;
import org.team10424102.whisky.components.auth.BlackServerAccount;
import org.team10424102.whisky.components.auth.PhoneTokenAuthentication;
import org.team10424102.whisky.controllers.MainActivity;
import org.team10424102.whisky.controllers.VcodeActivity;
import org.team10424102.whisky.controllers.WelcomeActivity;
import org.team10424102.whisky.controllers.posts.GameBindingsAdapter;
import org.team10424102.whisky.controllers.posts.PostsAdapter;
import org.team10424102.whisky.controllers.posts.PostsMyselfAdapter;
import org.team10424102.whisky.dev.DebugActivity;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionDeserializer;
import org.team10424102.whisky.models.extensions.PostExtensionManager;
import org.team10424102.whisky.models.extensions.dota2.Dota2PostExtensionHandler;
import org.team10424102.whisky.models.extensions.image.ImagePostExtensionHandler;
import org.team10424102.whisky.models.extensions.poll.PollPostExtensionHandler;
import org.team10424102.whisky.models.mapping.LazyImageDeserializer;
import org.team10424102.whisky.models.mapping.LazyImageSerializer;
import org.team10424102.whisky.ui.ActivitySliderView;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.JacksonConverterFactory;
import retrofit2.Retrofit;

@Module(
        injects = {
                WelcomeActivity.class,
                VcodeActivity.class,
                MainActivity.class,
                GameManager.class,
                PhoneTokenAuthentication.class,
                DebugActivity.class,
                AccountService.class,
                PostsAdapter.class,
                BlackServerAccount.class,
                GameBindingsAdapter.class,
                PostsMyselfAdapter.class,
                PostExtensionDeserializer.class,
                ActivitySliderView.class,
                PostExtensionManager.class
        },
        library = true
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

    @Provides @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client = client.newBuilder()
                .readTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(1,TimeUnit.SECONDS)
                .addInterceptor(new ApiAuthInterceptor(mContext))
                .addInterceptor(new LocalizationInterceptor(mContext))
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }

    @Provides @Singleton Picasso providePicasso(OkHttpClient client) {
        Picasso picasso = new Picasso
                .Builder(mContext)
                .downloader(new OkHttp3Downloader(client))
                .build();
        return picasso;
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();

        LazyImageSerializer serializer = new LazyImageSerializer();
        module.addSerializer(LazyImage.class, serializer);

        LazyImageDeserializer deserializer1 = new LazyImageDeserializer();
        module.addDeserializer(LazyImage.class, deserializer1);

        PostExtensionDeserializer deserializer2 = new PostExtensionDeserializer();
        module.addDeserializer(PostExtension.class, deserializer2);

        return objectMapper;
    }

    @Provides @Singleton
    Retrofit provideRetrofit(ObjectMapper objectMapper, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(App.getInstance().getHost())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
    }

    @Provides @Singleton BlackServerApi provideBlackServerApi(Retrofit retrofit) {
        return retrofit.create(BlackServerApi.class);
    }

    @Provides @Singleton GameManager provideGameManager(BlackServerApi api) {
        return new GameManager(api);
    }

    @Provides @Singleton PostExtensionManager providePostExtensionManager() {
        PostExtensionManager manager = new PostExtensionManager();

        Dota2PostExtensionHandler handler1 = new Dota2PostExtensionHandler();
        manager.registerPostExtensionHandler(handler1);

        ImagePostExtensionHandler handler2 = new ImagePostExtensionHandler();
        manager.registerPostExtensionHandler(handler2);

        PollPostExtensionHandler handler3 = new PollPostExtensionHandler();
        manager.registerPostExtensionHandler(handler3);

        return manager;
    }

    @Provides @Singleton AccountRepo provideAccountRepo(PersistenceService persistenceService) {
        AccountRepo repo = new AccountRepoImpl(persistenceService);
        return repo;
    }

    @Provides @Singleton ImageRepo provideImageRepo(PersistenceService persistenceService) {
        ImageRepo repo = new ImageRepoImpl(persistenceService);
        return repo;
    }

}
