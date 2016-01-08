package org.team10424102.whisky.components.auth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by yy on 11/7/15.
 */
public class ApiAuthInterceptor implements Interceptor {
    private static final String TAG = "ApiAuthInterceptor";
    private final CountDownLatch mBound = new CountDownLatch(1);
    private AccountService mAccountService;
    private final Context mContext;

    public ApiAuthInterceptor(Context context) {
        mContext = context;
        Intent intent = new Intent(context, AccountService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
                mAccountService = binder.getService();
                mBound.countDown();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        try {
            mBound.await();

            Account account = mAccountService.getCurrentAccount();

            if(account != null) {
                Authentication auth = account.getAuthentication();
                request = auth.authenticateHttpRequest(request);
            }

            return chain.proceed(request);

        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
