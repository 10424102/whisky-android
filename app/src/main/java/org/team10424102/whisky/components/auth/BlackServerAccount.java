package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.util.Log;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.controllers.VcodeActivity;
import org.team10424102.whisky.models.Profile;

public class BlackServerAccount implements Account {
    public static final String TAG = "BlackServerAccount";
    private Authentication mAuth;
    private boolean mValid;
    private boolean mVisiable;
    private Profile mProfile;
    private int usedCount;
    private String mPhone;

    public BlackServerAccount(){}
    public BlackServerAccount(String phone) {
        mPhone = phone;
    }

    public Authentication getAuth() {
        return mAuth;
    }

    public void setAuth(Authentication auth) {
        mAuth = auth;
    }

    public void setVisiable(boolean visiable) {
        mVisiable = visiable;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    @Override
    public void activate(Context context) {
        String phone = mPhone;
        ApiService apiService =
                (ApiService)((App)context.getApplicationContext()).getComponent(ApiService.class);
        try {
            if (mAuth == null) {
                if (apiService.isPhoneAvailable(phone).execute().code() == 404) {
                    Intent intent = new Intent(context, VcodeActivity.class);
                    intent.putExtra(VcodeActivity.EXTRA_TYPE, VcodeActivity.TYPE_REGISTER);
                    intent.putExtra(VcodeActivity.EXTRA_ACCOUNT, this);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, VcodeActivity.class);
                    intent.putExtra(VcodeActivity.EXTRA_TYPE, VcodeActivity.TYPE_REFRESH_TOKEN);
                    intent.putExtra(VcodeActivity.EXTRA_ACCOUNT, this);
                    context.startActivity(intent);
                }
            } else {
                if (mAuth.isAuthenticated(context)) {
                    // no-op
                } else {
                    Intent intent = new Intent(context, VcodeActivity.class);
                    intent.putExtra(VcodeActivity.EXTRA_TYPE, VcodeActivity.TYPE_REFRESH_TOKEN);
                    intent.putExtra(VcodeActivity.EXTRA_ACCOUNT, this);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "账户激活失败", e);
        }
    }

    @Override
    public boolean isValid(Context context) {
        if (mAuth == null) return false;
        return mAuth.isAuthenticated(context);
    }

    @Override
    public boolean isVisiable() {
        return mVisiable;
    }

    @Override
    public void setProfile(Profile profile) {
        mProfile = profile;
    }

    @NonNull
    @Override
    public Profile getProfile() {
        return mProfile;
    }

    @Override
    public void save(Context context) {
        AccountRepo accountRepo =
                (AccountRepo)((App)context.getApplicationContext()).getComponent(AccountRepo.class);
        accountRepo.save(this);
    }

    @NonNull
    @Override
    public Authentication getAuthentication() {
        if (mAuth == null) throw new IllegalStateException("Account 中 mAuth 为空");
        return mAuth;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        mAuth = authentication;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mAuth, 0);
        dest.writeByte(mValid ? (byte) 1 : (byte) 0);
        dest.writeByte(mVisiable ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mProfile, 0);
        dest.writeInt(this.usedCount);
        dest.writeString(this.mPhone);
    }

    protected BlackServerAccount(Parcel in) {
        this.mAuth = in.readParcelable(Authentication.class.getClassLoader());
        this.mValid = in.readByte() != 0;
        this.mVisiable = in.readByte() != 0;
        this.mProfile = in.readParcelable(Profile.class.getClassLoader());
        this.usedCount = in.readInt();
        this.mPhone = in.readString();
    }

    public static final Creator<BlackServerAccount> CREATOR = new Creator<BlackServerAccount>() {
        public BlackServerAccount createFromParcel(Parcel source) {
            return new BlackServerAccount(source);
        }

        public BlackServerAccount[] newArray(int size) {
            return new BlackServerAccount[size];
        }
    };
}
