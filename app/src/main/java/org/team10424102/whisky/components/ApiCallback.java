package org.team10424102.whisky.components;

import android.util.Log;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by yy on 11/13/15.
 */
public class ApiCallback<T> implements Callback<T> {
    public static final String TAG = "ApiCallback";
    private int code = 200;

    public ApiCallback(int expectedHttpStatusCode) {
        code = expectedHttpStatusCode;
    }

    public ApiCallback() {

    }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (code == response.code() && response.body() != null) {
            handleSuccess(response.body());
        } else {
            handleFailure(response, null);
        }
    }

    protected void handleSuccess(T result) {
    }

    protected void handleFailure(Response response, Throwable t) {
        Log.e(TAG, "API request failed.", t);
        if (response != null) {
            Log.e(TAG, String.format("Response[code = %d, body = %s]",
                    response.code(), response.body().toString()));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        handleFailure(null, t);
    }
}
