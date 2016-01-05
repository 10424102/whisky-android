package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonSetter;

import org.team10424102.whisky.App;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImage extends BaseObservable implements Parcelable {

    private String mAccessToken;
    private String mHash;
    private int mDefaultDrawableId;

    @Deprecated
    public LazyImage(String accessToken) {
        this(accessToken, null, App.DEFAULT_NO_IMAGE);
    }

    public LazyImage(String accessToken, String hash) {
        this(accessToken, hash, App.DEFAULT_NO_IMAGE);
    }

    public LazyImage(String accessToken, String hash, int defaultDrawableId) {
        mAccessToken = accessToken;
        mHash = hash;
        mDefaultDrawableId = defaultDrawableId;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getHash() {
        return mHash;
    }

    public void setHash(String hash) {
        mHash = hash;
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                     Object Override                         //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return String.format("图片 (哈希值 = %s, 访问令牌 = %s)", getHash(), getAccessToken());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LazyImage image = (LazyImage) o;

        return mHash.equals(image.mHash);

    }

    @Override
    public int hashCode() {
        return mHash.hashCode();
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAccessToken);
        dest.writeString(this.mHash);
    }

    protected LazyImage(Parcel in) {
        this.mAccessToken = in.readString();
        this.mHash = in.readString();
    }

    public static final Creator<LazyImage> CREATOR = new Creator<LazyImage>() {
        public LazyImage createFromParcel(Parcel source) {
            return new LazyImage(source);
        }

        public LazyImage[] newArray(int size) {
            return new LazyImage[size];
        }
    };
}
