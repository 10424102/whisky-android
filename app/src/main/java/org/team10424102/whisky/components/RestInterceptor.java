package org.team10424102.whisky.components;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.team10424102.whisky.Global;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by yy on 11/7/15.
 */
public class RestInterceptor implements Interceptor {
    public static final String[] NO_AUTH_URIS = new String[]{
            "/status", "/api/register"
    };
    private static final String TAG = "RestInterceptor";

    private boolean needAuthHeader(Request request) {
        String url = request.urlString();
        for (int i = 0; i < NO_AUTH_URIS.length; i++) {
            if (url.endsWith(NO_AUTH_URIS[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "拦截到请求 " + request.urlString());
        if (needAuthHeader(request)) {
            request = Global.authenticationManager.addAuthHeader(request);
        }
        Response response = chain.proceed(request);
        Log.d(TAG, "请求 " + request.urlString() + " 返回状态码：" + response.code() +
                " 多媒体数据类型：" + response.body().contentType().toString());
        switch (response.code()) {
            case HTTP_UNAUTHORIZED:
                // TODO refresh token
                break;
        }
        return response;
    }
}
