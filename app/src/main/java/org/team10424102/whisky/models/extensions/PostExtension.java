package org.team10424102.whisky.models.extensions;

import android.os.Parcel;
import android.os.Parcelable;

public class PostExtension implements Parcelable {

    public String id;
    public Parcelable data;

    public PostExtension(String id, Parcelable data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Parcelable getData() {
        return data;
    }

    public void setData(Parcelable data) {
        this.data = data;
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    protected PostExtension(Parcel in) {
        this.id = in.readString();
        this.data = in.readParcelable(Parcelable.class.getClassLoader());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.data, 0);
    }

    public static final Parcelable.Creator<PostExtension> CREATOR = new Parcelable.Creator<PostExtension>() {
        public PostExtension createFromParcel(Parcel source) {
            return new PostExtension(source);
        }

        public PostExtension[] newArray(int size) {
            return new PostExtension[size];
        }
    };
}
