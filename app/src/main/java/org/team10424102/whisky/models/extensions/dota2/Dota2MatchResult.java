package org.team10424102.whisky.models.extensions.dota2;

import android.os.Parcel;
import android.os.Parcelable;

public class Dota2MatchResult implements Parcelable {
    public static final Parcelable.Creator<Dota2MatchResult> CREATOR = new Parcelable.Creator<Dota2MatchResult>() {
        public Dota2MatchResult createFromParcel(Parcel source) {
            return new Dota2MatchResult(source);
        }

        public Dota2MatchResult[] newArray(int size) {
            return new Dota2MatchResult[size];
        }
    };
    private EDota2Hero hero;
    private String evaluation;
    private EDota2MatchType type;


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        GET & SET                            //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////
    private EMatchResult result;

    public Dota2MatchResult() {
    }

    protected Dota2MatchResult(Parcel in) {
        int tmpHero = in.readInt();
        this.hero = tmpHero == -1 ? null : EDota2Hero.values()[tmpHero];
        this.evaluation = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : EDota2MatchType.values()[tmpType];
        int tmpResult = in.readInt();
        this.result = tmpResult == -1 ? null : EMatchResult.values()[tmpResult];
    }

    public EDota2Hero getHero() {
        return hero;
    }

    public void setHero(EDota2Hero hero) {
        this.hero = hero;
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


    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    public void setType(EDota2MatchType type) {
        this.type = type;
    }

    public EMatchResult getResult() {
        return result;
    }

    public void setResult(EMatchResult result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hero == null ? -1 : this.hero.ordinal());
        dest.writeString(this.evaluation);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeInt(this.result == null ? -1 : this.result.ordinal());
    }
}
