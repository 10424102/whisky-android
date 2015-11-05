package org.team10424102.whisky.components;

import android.content.Context;

import org.team10424102.whisky.Global;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by yy on 11/13/15.
 */
public class ApiCallback<T> implements Callback<T> {
    private final int code;
    private final Context mContext;

    public ApiCallback(Context context, int expectedHttpStatusCode) {
        mContext = context;
        code = expectedHttpStatusCode;
    }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (code == response.code() && response.body() != null) {
            handleSuccess(response.body());
        } else {
            Global.errorManager.handleError(mContext, ErrorManager.ERR_INVOKING_API);
        }
    }

    public void handleSuccess(T result) {
        // no-op;
    }

    @Override
    public void onFailure(Throwable t) {
        Global.errorManager.handleError(mContext, ErrorManager.ERR_INVOKING_API);
        // TODO retry
    }
}
