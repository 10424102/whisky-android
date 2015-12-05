package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.team10424102.whisky.App;
import org.team10424102.whisky.BR;
import org.team10424102.whisky.models.enums.EGender;
import org.team10424102.whisky.utils.ConstellationUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yy on 11/4/15.
 */
public class Profile extends BaseObservable implements Parcelable {

    private String phone;
    private String username;
    private String email;
    private Date birthday;
    private String college;
    private String academy;
    private String grade;
    private String signature;
    private String hometown;
    private String highschool;
    private LazyImage avatar;
    private LazyImage background;
    private EGender gender = EGender.SECRET;
    private String nickname;
    private List<User> friends = new ArrayList<>();
    private List<User> fans = new ArrayList<>();
    private List<User> focuses = new ArrayList<>();
    private String token;




    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        GET & SET                            //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    @Bindable
    public LazyImage getAvatar() {
        return avatar;
    }

    public void setAvatar(LazyImage avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public LazyImage getBackground() {
        return background;
    }

    public void setBackground(LazyImage background) {
        this.background = background;
        notifyPropertyChanged(BR.background);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Bindable
    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    @Bindable
    public String getHighschool() {
        return highschool;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
    }

    @Bindable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Bindable
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Bindable
    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    @Bindable
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Bindable
    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFans() {
        return fans;
    }

    public void setFans(List<User> fans) {
        this.fans = fans;
    }

    public List<User> getFocuses() {
        return focuses;
    }

    public void setFocuses(List<User> focuses) {
        this.focuses = focuses;
    }

    public String getToken() {
        return token;
    }



    public void setToken(String token) {
        this.token = token;
    }



    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                          Others                             //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @Bindable
    public String getAge() {
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
        if (birthday != null) {
            int id = ConstellationUtils.getConstellation(birthday).getStringResId();
            return App.getContext().getString(id);
        }
        return null;
    }





    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                     Object Override                         //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return String.format("个人资料 (用户名 = %s, 昵称 = %s, 手机号 = %s)",
                getUsername(), getNickname(), getPhone());
    }




    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        Parcelable                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////


    public Profile() {
    }

    protected Profile(Parcel in) {
        this.phone = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        this.college = in.readString();
        this.academy = in.readString();
        this.grade = in.readString();
        this.signature = in.readString();
        this.hometown = in.readString();
        this.highschool = in.readString();
        this.avatar = in.readParcelable(LazyImage.class.getClassLoader());
        this.background = in.readParcelable(LazyImage.class.getClassLoader());
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : EGender.values()[tmpGender];
        this.nickname = in.readString();
        this.friends = in.createTypedArrayList(User.CREATOR);
        this.fans = in.createTypedArrayList(User.CREATOR);
        this.focuses = in.createTypedArrayList(User.CREATOR);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeLong(birthday != null ? birthday.getTime() : -1);
        dest.writeString(this.college);
        dest.writeString(this.academy);
        dest.writeString(this.grade);
        dest.writeString(this.signature);
        dest.writeString(this.hometown);
        dest.writeString(this.highschool);
        dest.writeParcelable(this.avatar, 0);
        dest.writeParcelable(this.background, 0);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeString(this.nickname);
        dest.writeTypedList(friends);
        dest.writeTypedList(fans);
        dest.writeTypedList(focuses);
    }


    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
