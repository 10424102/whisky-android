package org.team10424102.whisky.components;

import android.content.Context;
import android.support.annotation.NonNull;

import org.team10424102.whisky.App;

public class PhoneTokenAuthentication implements Authentication {
    private boolean mAuthenticated;
    private String mPhone;
    private String mToken;
    private final AuthenticationService mAuthenticationService;
    private final Context mContext;

    public PhoneTokenAuthentication(@NonNull Context context,
                                    @NonNull String phone, @NonNull String token) {
        mContext = context;
        App app = (App) context.getApplicationContext();
        mAuthenticationService =
                (AuthenticationService) app.getComponent(AuthenticationService.class);
        setPhone(phone);
        setToken(token);
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public boolean isAuthenticated() {
        return mAuthenticated;
    }

    @Override
    public boolean authenticate() {
        return mAuthenticated = mAuthenticationService.authenticate(this);
    }
}
