package org.team10424102.whisky.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import org.team10424102.whisky.models.enums.EGameType;

public class Game implements Parcelable {
    @StringRes
    private int name;
    private EGameType type;
    private LazyImage logo;



    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public EGameType getType() {
        return type;
    }

    public void setType(EGameType type) {
        this.type = type;
    }

    public LazyImage getLogo() {
        return logo;
    }

    public void setLogo(LazyImage logo) {
        this.logo = logo;
    }






    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////



    public Game() {
    }

    protected Game(Parcel in) {
        this.name = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : EGameType.values()[tmpType];
        this.logo = in.readParcelable(LazyImage.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.name);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.logo, 0);
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
