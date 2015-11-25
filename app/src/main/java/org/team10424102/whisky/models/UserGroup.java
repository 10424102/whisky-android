package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yy on 11/14/15.
 */
public class UserGroup extends BaseObservable implements Parcelable {
    public static final Creator<UserGroup> CREATOR = new Creator<UserGroup>() {
        public UserGroup createFromParcel(Parcel source) {
            return new UserGroup(source);
        }

        public UserGroup[] newArray(int size) {
            return new UserGroup[size];
        }
    };
    private Long id;
    private String name;
    private List<User> members;

    public UserGroup() {
    }

    protected UserGroup(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.members = in.createTypedArrayList(User.CREATOR);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return String.format("用户组 (编号 = %d, 组名 = %s)", getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroup userGroup = (UserGroup) o;

        return !(id != null ? !id.equals(userGroup.id) : userGroup.id != null);

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
        dest.writeString(this.name);
        dest.writeTypedList(members);
    }
}
