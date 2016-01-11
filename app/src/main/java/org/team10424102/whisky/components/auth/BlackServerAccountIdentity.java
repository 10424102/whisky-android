package org.team10424102.whisky.components.auth;

import android.os.Parcel;

public class BlackServerAccountIdentity implements AccountIdentity<String> {

    private String mPhone;

    public BlackServerAccountIdentity(String phone) {
        mPhone = phone;
    }

    @Override
    public String get() {
        return mPhone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPhone);
    }

    protected BlackServerAccountIdentity(Parcel in) {
        this.mPhone = in.readString();
    }

    public static final Creator<BlackServerAccountIdentity> CREATOR = new Creator<BlackServerAccountIdentity>() {
        public BlackServerAccountIdentity createFromParcel(Parcel source) {
            return new BlackServerAccountIdentity(source);
        }

        public BlackServerAccountIdentity[] newArray(int size) {
            return new BlackServerAccountIdentity[size];
        }
    };
}
