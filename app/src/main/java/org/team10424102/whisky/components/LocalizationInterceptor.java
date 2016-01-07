package org.team10424102.whisky.components;

import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Locale;

public class LocalizationInterceptor implements Interceptor {


    private String mLocale;

    public LocalizationInterceptor(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        mLocale = locale.toString();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();
        //req = req.newBuilder().addHeader("Accept-Language", mLocale).build();
        req = req.newBuilder().addHeader("Accept-Language", "zh_CN").build();

        return chain.proceed(req);
    }
}
