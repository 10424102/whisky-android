package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import org.team10424102.whisky.App;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImage extends BaseObservable implements Parcelable {

    private String accessToken;
    private String hash;

    public LazyImage(String accessToken) {
        this.accessToken = accessToken;
    }


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        GET & SET                            //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////



    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

        LazyImage lazyImage = (LazyImage) o;

        return !(hash != null ? !hash.equals(lazyImage.hash) : lazyImage.hash != null);

    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }




    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////




    protected LazyImage(Parcel in) {
        this.accessToken = in.readString();
        this.hash = in.readString();
    }

    public LazyImage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeString(this.hash);
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
