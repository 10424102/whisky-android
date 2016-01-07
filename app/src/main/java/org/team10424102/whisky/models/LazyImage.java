package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.ImageRepo;
import org.team10424102.whisky.components.ImageViewWrapper;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 11/14/15.
 */
public class LazyImage extends BaseObservable implements Parcelable {

    private String mAccessToken;
    private String mHash;
    private int mDefaultDrawableId = App.DEFAULT_NO_IMAGE;
    @Inject Picasso mPicasso;
    @Inject ImageRepo mImageRepo;

    @Deprecated
    public LazyImage(String accessToken) {
        this(accessToken, null, App.DEFAULT_NO_IMAGE);
    }

    public LazyImage(String accessToken, String hash) {
        this(accessToken, hash, App.DEFAULT_NO_IMAGE);
    }

    public LazyImage(String accessToken, String hash, int defaultDrawableId) {
        App.getInstance().getObjectGraph().inject(this);
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

    public void loadInto(ImageView imageView) {
        Observable.just(mHash)
                .map(mImageRepo::getImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        } else {
                            mPicasso.load(uri()).into(new ImageViewWrapper(imageView, mHash));
                        }
                    }
                });
    }

    public Uri uri() {
        return Uri.parse(App.getInstance().getHost() + "/api/images?q=" + mAccessToken);
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
        App.getInstance().getObjectGraph().inject(this);
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
