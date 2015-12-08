package org.team10424102.whisky.components;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.team10424102.whisky.App;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by yy on 11/7/15.
 */
public class ApiAuthInterceptor implements Interceptor {
    public static final String[] NO_AUTH_URIS = new String[]{
            App.API_TOKEN, App.API_HEALTH
    };
    private static final String TAG = "ApiAuthInterceptor";

    private boolean needAuthHeader(Request request) {
        String url = request.urlString();
        for (String uri : NO_AUTH_URIS) {
            if (url.endsWith(uri)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Log.d(TAG, String.format("%s %s", request.method(), request.url().toString()));

        if (needAuthHeader(request)) {
            // 添加 Token
            if (App.getProfile().getToken() != null) {
                request = request.newBuilder()
                        .addHeader("X-Token", App.getProfile().getToken())
                        .build();
            }
        }

        Response response = chain.proceed(request);

        if (response.code() == HTTP_UNAUTHORIZED) {
            //App.authenticateCurrentUser();
        }

        return response;
    }
}
