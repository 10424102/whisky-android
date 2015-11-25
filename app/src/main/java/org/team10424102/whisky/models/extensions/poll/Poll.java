package org.team10424102.whisky.models.extensions.poll;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Poll implements Parcelable {
    public static final Parcelable.Creator<Poll> CREATOR = new Parcelable.Creator<Poll>() {
        public Poll createFromParcel(Parcel source) {
            return new Poll(source);
        }

        public Poll[] newArray(int size) {
            return new Poll[size];
        }
    };
    private String question;
    private List<String> options;

    public Poll() {
    }

    protected Poll(Parcel in) {
        this.question = in.readString();
        this.options = in.createStringArrayList();
    }

    public String getQuestion() {
        return question;
    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeStringList(this.options);
    }
}
