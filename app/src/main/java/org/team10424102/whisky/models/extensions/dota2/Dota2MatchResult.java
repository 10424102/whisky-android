package org.team10424102.whisky.models.extensions.dota2;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.team10424102.whisky.models.LazyImage;

public class Dota2MatchResult implements Parcelable {

    private String heroName;
    private LazyImage heroAvatar;
    private String evaluation;
    private EDota2MatchType type;
    private EMatchResult result;

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        GET & SET                            //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @JsonProperty("hero")
    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public LazyImage getHeroAvatar() {
        return heroAvatar;
    }

    public void setHeroAvatar(LazyImage heroAvatar) {
        this.heroAvatar = heroAvatar;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public EDota2MatchType getType() {
        return type;
    }

    public void setType(EDota2MatchType type) {
        this.type = type;
    }

    public EMatchResult getResult() {
        return result;
    }

    public void setResult(EMatchResult result) {
        this.result = result;
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
        dest.writeString(this.heroName);
        dest.writeParcelable(this.heroAvatar, 0);
        dest.writeString(this.evaluation);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeInt(this.result == null ? -1 : this.result.ordinal());
    }

    public Dota2MatchResult() {
    }

    protected Dota2MatchResult(Parcel in) {
        this.heroName = in.readString();
        this.heroAvatar = in.readParcelable(LazyImage.class.getClassLoader());
        this.evaluation = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : EDota2MatchType.values()[tmpType];
        int tmpResult = in.readInt();
        this.result = tmpResult == -1 ? null : EMatchResult.values()[tmpResult];
    }

    public static final Creator<Dota2MatchResult> CREATOR = new Creator<Dota2MatchResult>() {
        public Dota2MatchResult createFromParcel(Parcel source) {
            return new Dota2MatchResult(source);
        }

        public Dota2MatchResult[] newArray(int size) {
            return new Dota2MatchResult[size];
        }
    };
}
