package org.team10424102.whisky.components.auth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.team10424102.whisky.App;
import org.team10424102.whisky.BR;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.PostLike;
import org.team10424102.whisky.models.User;
import org.team10424102.whisky.models.enums.Gender;
import org.team10424102.whisky.utils.ConstellationUtils;
import org.team10424102.whisky.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yy on 11/4/15.
 */
public class BlackServerUserProfile extends BaseObservable implements Profile {
    public static final String PHONE = "phone";
    public static final String USERNAME = "username";
    public static final String NICKNAME = "nickname";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";
    public static final String COLLEGE = "college";
    public static final String ACADEMY = "academy";
    public static final String GRADE = "grade";
    public static final String SIGNATURE = "signature";
    public static final String HOMETOWN = "hometown";
    public static final String HIGHSCHOOL = "highschool";
    public static final String GENDER = "gender";
    public static final String AVATAR = "avatar";
    public static final String BACKGROUND = "background";
    public static final String FRIENDS = "friends";
    public static final String FANS = "fans";
    public static final String FOCUSES = "focuses";
    public static final String LIKES = "likes";
    public static final String GROUPS = "groups";

    public static final Set<String> PROPERTIES;

    static {
        PROPERTIES = new HashSet<>();
        PROPERTIES.add(PHONE);
        PROPERTIES.add(USERNAME);
        PROPERTIES.add(NICKNAME);
        PROPERTIES.add(EMAIL);
        PROPERTIES.add(BIRTHDAY);
        PROPERTIES.add(COLLEGE);
        PROPERTIES.add(ACADEMY);
        PROPERTIES.add(GRADE);
        PROPERTIES.add(SIGNATURE);
        PROPERTIES.add(HOMETOWN);
        PROPERTIES.add(HIGHSCHOOL);
        PROPERTIES.add(GENDER);
        PROPERTIES.add(AVATAR);
        PROPERTIES.add(BACKGROUND);
        PROPERTIES.add(FRIENDS);
        PROPERTIES.add(FANS);
        PROPERTIES.add(FOCUSES);
        PROPERTIES.add(LIKES);
        PROPERTIES.add(GROUPS);
    }

    private Properties mProperties = new Properties();

    public BlackServerUserProfile() {
        App app = App.getInstance();
        mProperties.put("username", app.getString(R.string.default_username));
        mProperties.put("nickname", app.getString(R.string.default_nickname));
        mProperties.put("gender", app.getString(R.string.gender_unknown));
    }

    @Override
    public Object getProperty(String name) {
        return mProperties.get(name);
    }

    @Override
    public void setProperty(String name, Object value) {
        if (value == null) {
            mProperties.remove(name);
            return;
        }
        if (!PROPERTIES.contains(name)) {
            throw new RuntimeException("no such property: " + name);
        }
        mProperties.put(name, value);
        switch (name) {
            case PHONE:
                notifyPropertyChanged(BR.phone);
                break;
            case USERNAME:
                notifyPropertyChanged(BR.username);
                break;
            case NICKNAME:
                notifyPropertyChanged(BR.nickname);
                break;
            case EMAIL:
                notifyPropertyChanged(BR.email);
                break;
            case BIRTHDAY:
                notifyPropertyChanged(BR.birthday);
                break;
            case COLLEGE:
                notifyPropertyChanged(BR.college);
                break;
            case ACADEMY:
                notifyPropertyChanged(BR.academy);
                break;
            case GRADE:
                notifyPropertyChanged(BR.grade);
                break;
            case SIGNATURE:
                notifyPropertyChanged(BR.signature);
                break;
            case HOMETOWN:
                notifyPropertyChanged(BR.hometown);
                break;
            case HIGHSCHOOL:
                notifyPropertyChanged(BR.highschool);
                break;
            case GENDER:
                notifyPropertyChanged(BR.gender);
                break;
            case AVATAR:
                notifyPropertyChanged(BR.avatar);
                break;
            case BACKGROUND:
                notifyPropertyChanged(BR.background);
                break;
            case FRIENDS:
                notifyPropertyChanged(BR.friends);
                break;
            case FANS:
                notifyPropertyChanged(BR.fans);
                break;
            case FOCUSES:
                notifyPropertyChanged(BR.focuses);
                break;
            case LIKES:
                notifyPropertyChanged(BR.likes);
                break;
        }
    }

    @Override
    public String[] getAllPropertyNames() {
        return (String[]) PROPERTIES.toArray();
    }

    @Override
    public Properties getAllProperties() {
        return new Properties(mProperties);
    }


    @Bindable
    public String getUsername() {
        return (String) getProperty(USERNAME);
    }

    @Bindable
    public String getNickname() {
        return (String) getProperty(NICKNAME);
    }

    @Bindable
    public LazyImage getAvatar() {
        return (LazyImage) getProperty(AVATAR);
    }

    @Bindable
    public LazyImage getBackground() {
        return (LazyImage) getProperty(BACKGROUND);
    }

    @Bindable
    public String getPhone() {
        return (String) getProperty(USERNAME);
    }

    @Bindable
    public String getEmail() {
        return (String) getProperty(EMAIL);
    }

    @Bindable
    public String getSignature() {
        return (String) getProperty(SIGNATURE);
    }

    @Bindable
    public String getHometown() {
        return (String) getProperty(HOMETOWN);
    }

    @Bindable
    public String getHighschool() {
        return (String) getProperty(HIGHSCHOOL);
    }

    @Bindable
    public String getCollege() {
        return (String) getProperty(COLLEGE);
    }

    @Bindable
    public String getAcademy() {
        return (String) getProperty(ACADEMY);
    }

    @Bindable
    public String getGrade() {
        return (String) getProperty(GRADE);
    }

    @Bindable
    public String getGender() {
        int gender = (int) getProperty(GENDER);
        switch (gender) {
            case GENDER_MALE:
                return App.getInstance().getString(R.string.gender_male);
            case GENDER_FEMALE:
                return App.getInstance().getString(R.string.gender_female);
            case GENDER_OTHER:
                return App.getInstance().getString(R.string.gender_other);
            case GENDER_UNKNOWN:
                return App.getInstance().getString(R.string.gender_unknown);
        }
        throw new RuntimeException("no such gender type: " + gender);
    }

    @Bindable
    public String getAge() {
        Date birthday = (Date) getProperty(BIRTHDAY);
        if (birthday != null) {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            calendar.setTime(birthday);
            int birthYear = calendar.get(Calendar.YEAR);
            int age = currentYear - birthYear + 1;
            return Integer.toString(age);
        }
        return null;
    }

    @Bindable
    public String getConstellation() {
        Date birthday = (Date) getProperty(BIRTHDAY);
        if (birthday != null) {
            int id = ConstellationUtils.getConstellation(birthday).getStringResId();
            return StringUtils.getString(id);
        }
        return null;
    }


    @Bindable
    @SuppressWarnings("unchecked")
    public LazySet<User> getFriends() {
        return (LazySet<User>) getProperty(FRIENDS);
    }

    @Bindable
    @SuppressWarnings("unchecked")
    public LazySet<User> getFans() {
        return (LazySet<User>) getProperty(FANS);
    }

    @Bindable
    @SuppressWarnings("unchecked")
    public LazySet<User> getFocuses() {
        return (LazySet<User>) getProperty(FOCUSES);
    }

    @Bindable
    @SuppressWarnings("unchecked")
    public LazySet<User> getLikes() {
        return (LazySet<User>) getProperty(LIKES);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mProperties);
    }

    protected BlackServerUserProfile(Parcel in) {
        this.mProperties = (Properties) in.readSerializable();
    }

    public static final Creator<BlackServerUserProfile> CREATOR = new Creator<BlackServerUserProfile>() {
        public BlackServerUserProfile createFromParcel(Parcel source) {
            return new BlackServerUserProfile(source);
        }

        public BlackServerUserProfile[] newArray(int size) {
            return new BlackServerUserProfile[size];
        }
    };


    public static class Builder {
        private BlackServerUserProfile mProfile = new BlackServerUserProfile();

        public Builder phone(String value) {
            if (value == null) return this;
            mProfile.setProperty(PHONE, value);
            return this;
        }

        public Builder username(String value) {
            if (value == null) return this;
            mProfile.setProperty(USERNAME, value);
            return this;
        }

        public Builder nickname(String value) {
            if (value == null) return this;
            mProfile.setProperty(NICKNAME, value);
            return this;
        }

        public Builder email(String value) {
            if (value == null) return this;
            mProfile.setProperty(EMAIL, value);
            return this;
        }

        /**
         * @param year  > 0
         * @param month 1-12
         * @param day   1-31
         * @return
         */
        public Builder birthday(int year, int month, int day) {
            if (year <= 0 || month <= 0 || day <= 0) return this;
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            mProfile.setProperty(BIRTHDAY, cal.getTime());
            return this;
        }

        public Builder college(String value) {
            if (value == null) return this;
            mProfile.setProperty(COLLEGE, value);
            return this;
        }

        public Builder academy(String value) {
            if (value == null) return this;
            mProfile.setProperty(ACADEMY, value);
            return this;
        }

        public Builder grade(String value) {
            if (value == null) return this;
            mProfile.setProperty(GRADE, value);
            return this;
        }

        public Builder signature(String value) {
            if (value == null) return this;
            mProfile.setProperty(SIGNATURE, value);
            return this;
        }

        public Builder hometown(String value) {
            if (value == null) return this;
            mProfile.setProperty(HOMETOWN, value);
            return this;
        }

        public Builder highschool(String value) {
            if (value == null) return this;
            mProfile.setProperty(HIGHSCHOOL, value);
            return this;
        }

        public Builder gender(int value) {
            if (value == 0) return this;
            mProfile.setProperty(GENDER, value);
            return this;
        }

        public Builder avatar(String accessToken, String hash) {
            if (accessToken == null && hash == null) return this;
            mProfile.setProperty(AVATAR, new LazyImage(accessToken, hash, App.DEFAULT_AVATAR));
            return this;
        }

        public Builder background(String accessToken, String hash) {
            if (accessToken == null && hash == null) return this;
            mProfile.setProperty(BACKGROUND, new LazyImage(accessToken, hash, App.DEFAULT_BACKGROUND));
            return this;
        }

        public Builder friends(int count) {
            if (count <= 0) return this;
            LazySet<User> friends = new LazySet<>(count);
            mProfile.setProperty(FRIENDS, friends);
            return this;
        }

        public Builder fans(int count) {
            if (count <= 0) return this;
            LazySet<User> fans = new LazySet<>(count);
            mProfile.setProperty(FANS, fans);
            return this;
        }

        public Builder focuses(int count) {
            if (count <= 0) return this;
            LazySet<User> focues = new LazySet<>(count);
            mProfile.setProperty(FOCUSES, focues);
            return this;
        }

        public Builder likes(int count) {
            if (count <= 0) return this;
            LazySet<PostLike> likes = new LazySet<>(count);
            mProfile.setProperty(LIKES, likes);
            return this;
        }

        public BlackServerUserProfile build() {
            return mProfile;
        }
    }

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_OTHER = 3;
    public static final int GENDER_UNKNOWN = 4;

    public static final int[] GENDERS = {
            GENDER_MALE, GENDER_FEMALE, GENDER_OTHER, GENDER_UNKNOWN
    };
}
