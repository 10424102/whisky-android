package org.team10424102.whisky.models.extensions;

import android.os.Parcel;
import android.os.Parcelable;

public class PostExtensionData implements Parcelable {
    public static final Parcelable.Creator<PostExtensionData> CREATOR = new Parcelable.Creator<PostExtensionData>() {
        public PostExtensionData createFromParcel(Parcel source) {
            return new PostExtensionData(source);
        }

        public PostExtensionData[] newArray(int size) {
            return new PostExtensionData[size];
        }
    };
    public String id;
    public Parcelable data;

    public PostExtensionData(String id, Parcelable data) {
        this.id = id;
        this.data = data;
    }

    protected PostExtensionData(Parcel in) {
        this.id = in.readString();
        this.data = in.readParcelable(Parcelable.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    public Parcelable getData() {
        return data;
    }

    public void setData(Parcelable data) {
        this.data = data;
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
}
