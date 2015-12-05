package org.team10424102.whisky.models.extensions.image;

import android.os.Parcel;
import android.os.Parcelable;

import org.team10424102.whisky.models.LazyImage;

import java.util.ArrayList;
import java.util.List;

public class Gallery implements Parcelable {

    private List<LazyImage> images = new ArrayList<>();


    public List<LazyImage> getImages() {
        return images;
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


    public Gallery() {
    }

    protected Gallery(Parcel in) {
        this.images = in.createTypedArrayList(LazyImage.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(images);
    }

    public static final Parcelable.Creator<Gallery> CREATOR = new Parcelable.Creator<Gallery>() {
        public Gallery createFromParcel(Parcel source) {
            return new Gallery(source);
        }

        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };
}
