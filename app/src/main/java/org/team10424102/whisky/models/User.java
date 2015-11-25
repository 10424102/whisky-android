package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yy on 11/14/15.
 */
public class User extends BaseObservable implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private Long id;
    private LazyImage avatar;
    private String username;
    private String nickname;
    private String signature;

    public User() {
    }

    protected User(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.avatar = in.readParcelable(LazyImage.class.getClassLoader());
        this.username = in.readString();
        this.nickname = in.readString();
        this.signature = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LazyImage getAvatar() {
        return avatar;
    }

    public void setAvatar(LazyImage avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        if (nickname != null) return nickname;
        return username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return String.format("用户 (编号 = %d, 名字: %s)", getId(), getDisplayName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.username);
        dest.writeString(this.nickname);
        dest.writeString(this.signature);
    }
}
