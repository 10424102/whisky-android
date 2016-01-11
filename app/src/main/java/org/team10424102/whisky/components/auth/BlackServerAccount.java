package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Looper;
import android.os.Parcel;
import android.support.annotation.NonNull;

import org.team10424102.whisky.App;
import org.team10424102.whisky.BR;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.controllers.VcodeActivity;

import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import timber.log.Timber;

public class BlackServerAccount extends BaseObservable implements Account {
    private Authentication mAuth;
    private boolean mValid;
    private boolean mVisiable;
    private BlackServerUserProfile mProfile;
    private int mUsedCount;
    private BlackServerAccountIdentity mPhoneIdentity;

    @Inject BlackServerApi mApi;
    @Inject AccountRepo mAccountRepo;

    public BlackServerAccount(String phone) {
        App.getInstance().getObjectGraph().inject(this);
        mPhoneIdentity = new BlackServerAccountIdentity(phone);
    }

    public void setToken(String token) {
        mAuth = new PhoneTokenAuthentication(mPhoneIdentity.get(), token);
    }

    public void setVisiable(boolean visiable) {
        mVisiable = visiable;
    }

    public int getUsedCount() {
        return mUsedCount;
    }

    public void setUsedCount(int usedCount) {
        mUsedCount = usedCount;
    }

    @Override
    public void activate(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Timber.e("DO NOT activate account on the main thread!");
            throw new RuntimeException("activate account on main thread");
        }
        String phone = mPhoneIdentity.get();
        try {
            if (mAuth == null) {
                if (mApi.isPhoneAvailable(phone).execute().code() == 404) {
                    jumpVcodeActivity(context, VcodeActivity.TYPE_REGISTER);
                } else {
                    jumpVcodeActivity(context, VcodeActivity.TYPE_REFRESH_TOKEN);
                }
            } else {
                jumpVcodeActivity(context, VcodeActivity.TYPE_REFRESH_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("account activation failed: %s", this), e);
        }
    }

    private void jumpVcodeActivity(Context context, int type) throws InterruptedException {
        Intent intent = new Intent(context, VcodeActivity.class);
        intent.putExtra(VcodeActivity.EXTRA_TYPE, type);
        intent.putExtra(VcodeActivity.EXTRA_ACCOUNT, this);
        VcodeActivity.finishedLock = new CountDownLatch(1);
        context.startActivity(intent);
        VcodeActivity.finishedLock.await();
    }

    @Override
    public boolean isValid() {
        if (mAuth == null) return false;
        return mAuth.isAuthenticated();
    }

    @Override
    public boolean isVisiable() {
        return mVisiable;
    }

    public void setProfile(BlackServerUserProfile profile) {
        mProfile = profile;
        notifyPropertyChanged(BR.profile);
    }

    @NonNull
    @Override
    @Bindable
    public BlackServerUserProfile getProfile() {
        if (mProfile == null) throw new RuntimeException("mProfile is null");
        return mProfile;
    }

    @NonNull
    @Override
    public Authentication getAuthentication() {
        if (mAuth == null) throw new RuntimeException("mAuth is null");
        return mAuth;
    }

    @Override
    public BlackServerAccountIdentity getIdentity() {
        return mPhoneIdentity;
    }

    @Override
    public void save(Context context) {
        mAccountRepo.save(this);
    }

    @Override
    public String toString() {
        return String.format("BlackServerAccount[phone=%s, auth=%s, profile=%s, valid=%s, visiable=%s, usedCount=%d]",
                mPhoneIdentity.get(), mAuth, mProfile, mValid, mVisiable, mUsedCount);
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
        dest.writeInt(this.mUsedCount);
        dest.writeParcelable(this.mPhoneIdentity, 0);
    }

    protected BlackServerAccount(Parcel in) {
        this.mAuth = in.readParcelable(Authentication.class.getClassLoader());
        this.mValid = in.readByte() != 0;
        this.mVisiable = in.readByte() != 0;
        this.mProfile = in.readParcelable(BlackServerUserProfile.class.getClassLoader());
        this.mUsedCount = in.readInt();
        this.mPhoneIdentity = in.readParcelable(BlackServerAccountIdentity.class.getClassLoader());
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
