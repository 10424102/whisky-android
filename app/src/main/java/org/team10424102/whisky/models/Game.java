package org.team10424102.whisky.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game implements Parcelable {
    private String name;
    private String identifier;
    private LazyImage logo;

    @JsonProperty("localizedName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.identifier);
        dest.writeParcelable(this.logo, 0);
    }

    public Game() {
    }

    protected Game(Parcel in) {
        this.name = in.readString();
        this.identifier = in.readString();
        this.logo = in.readParcelable(LazyImage.class.getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
