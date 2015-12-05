package org.team10424102.whisky.components;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Locale;

public class LocalizationInterceptor implements Interceptor {


    private String mLocale;

    public LocalizationInterceptor(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        mLocale = locale.getDisplayName();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();
        req = req.newBuilder().addHeader("Accept-Language", mLocale).build();

        return chain.proceed(req);
    }
}
