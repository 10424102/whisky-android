package org.team10424102.whisky.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.ImageRepo;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@JsonDeserialize(using = LazyImageDeserializer.class)
@JsonSerialize(using = LazyImageSerializer.class)
public class LazyImage extends BaseModel implements Image {

    /**
     * 访问令牌字符串
     */
    private String token;

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * 哈希校验字符串
     */
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @DrawableRes private int def = App.DEFAULT_NO_IMAGE;
    @Inject Picasso picasso;
    @Inject ImageRepo imageRepo;

    @Override
    protected Observable<LazyImage> init() {
        return null;
    }

    public LazyImage() {
        super();
    }

    public LazyImage(String accessToken, String hash) {
        this(accessToken, hash, App.DEFAULT_NO_IMAGE);
    }

    public LazyImage(String accessToken, String hash, @DrawableRes int defaultDrawableId) {
        super();
        this.token = accessToken;
        this.hash = hash;
        this.def = defaultDrawableId;
    }

    @Override
    public void loadInto(final ImageView imageView) {
        Observable.just(hash)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String hash) {
                        return imageRepo.getImage(hash);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (bitmap != null) {
                            Timber.d("load image from local storage");
                            imageView.setImageBitmap(bitmap);
                        } else {
                            picasso.load(uri()).into(new ImageViewWrapper(imageView, hash));
                        }
                    }
                });
    }

    @Override
    public Uri uri() {
        return Uri.parse(App.getInstance().getHost() + "/api/images?q=" + token);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.hash);
    }

    protected LazyImage(Parcel in) {
        this.token = in.readString();
        this.hash = in.readString();
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
