package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.team10424102.whisky.BR;
import org.team10424102.whisky.utils.ConstellationUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yy on 11/4/15.
 */
@JsonIgnoreProperties({"realName", "idCard"})
public class Profile extends BaseObservable {

    private String phone;

    private String username;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    private String college;

    private String academy;

    private String grade;

    private String signature;

    private String hometown;

    private String highschool;

    private LazyImage avatar;

    private LazyImage background;

    private Gender gender;

    public void copyAndUpdateUI(Profile profile) {
        setPhone(profile.phone);
        setUsername(profile.username);
        setEmail(profile.email);
        setBirthday(profile.birthday);
        setCollege(profile.college);
        setAcademy(profile.academy);
        setGrade(profile.grade);
        setSignature(profile.signature);
        setHometown(profile.hometown);
        setHighschool(profile.highschool);
        setGender(profile.gender);
    }

    //<editor-fold desc="Getters & Setters">
    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
        notifyPropertyChanged(BR.age);
        notifyPropertyChanged(BR.constellation);
    }

    @Bindable
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
        notifyPropertyChanged(BR.college);
    }

    @Bindable
    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
        notifyPropertyChanged(BR.academy);
    }

    @Bindable
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        notifyPropertyChanged(BR.grade);
    }

    @Bindable
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.signature);
    }

    @Bindable
    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
        notifyPropertyChanged(BR.hometown);
    }

    @Bindable
    public String getHighschool() {
        return highschool;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
        notifyPropertyChanged(BR.highschool);
    }

    @Bindable
    public int getAge() {
        if (birthday == null) return 0;
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int birthYear = calendar.get(Calendar.YEAR);
        return currentYear - birthYear + 1;
    }

    @Bindable
    public String getConstellation() {
        return ConstellationUtils.getConstellation(birthday);
    }

    public LazyImage getAvatar() {
        return avatar;
    }

    public void setAvatar(LazyImage avatar) {
        this.avatar = avatar;
    }

    public LazyImage getBackground() {
        return background;
    }

    public void setBackground(LazyImage background) {
        this.background = background;
    }

    @Bindable
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    //</editor-fold>

    public enum Gender {
        MALE, FEMALE, SECRET
    }
}
