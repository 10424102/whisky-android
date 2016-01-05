package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.Request;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.api.ApiService;

public class PhoneTokenAuthentication implements Authentication {
    public static final String TAG = "PhoneTokenAuth...";
    public static final String AUTH_HEADER = "X-Token";
    private boolean mAuthenticated;
    private String mPhone;
    private String mToken;

    public PhoneTokenAuthentication(@NonNull String phone, @NonNull String token) {
        mPhone = phone;
        mToken = token;
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
    public boolean isAuthenticated(Context context) {
        ApiService apiService =
                (ApiService)((App)context.getApplicationContext()).getComponent(ApiService.class);
        try {
            if (apiService.isTokenAvailable(mToken).execute().code() == 200) return true;
        }catch (Exception e) {
            Log.d(TAG, "无法验证账户令牌是否有效", e);
        }
        return false;
    }

    @Override
    public Request authenticateHttpRequest(Request request) {
        String url = request.urlString();
        if (url.contains(App.API_USER + "/token")) return request;
        if (url.contains(App.API_STATUS)) return request;

        return request.newBuilder().addHeader(AUTH_HEADER, mToken).build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(mAuthenticated ? (byte) 1 : (byte) 0);
        dest.writeString(this.mPhone);
        dest.writeString(this.mToken);
    }

    protected PhoneTokenAuthentication(Parcel in) {
        this.mAuthenticated = in.readByte() != 0;
        this.mPhone = in.readString();
        this.mToken = in.readString();
    }

    public static final Creator<PhoneTokenAuthentication> CREATOR = new Creator<PhoneTokenAuthentication>() {
        public PhoneTokenAuthentication createFromParcel(Parcel source) {
            return new PhoneTokenAuthentication(source);
        }

        public PhoneTokenAuthentication[] newArray(int size) {
            return new PhoneTokenAuthentication[size];
        }
    };
}
