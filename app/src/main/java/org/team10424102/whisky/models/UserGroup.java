package org.team10424102.whisky.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import java.security.acl.Group;
import java.util.List;

import rx.Observable;

/**
 * Created by yy on 11/14/15.
 */
public class UserGroup extends BaseModel {

    /**
     * 组名
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 组员
     */
    private List<User> members;

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public UserGroup(long id) {
        super(id);
    }

    @Override
    protected Observable<UserGroup> init() {
        return api.getGroup(id);
    }

    protected UserGroup(Parcel in) {
        super(in);
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.members = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(members);
    }

    public static final Creator<UserGroup> CREATOR = new Creator<UserGroup>() {
        public UserGroup createFromParcel(Parcel source) {
            return new UserGroup(source);
        }

        public UserGroup[] newArray(int size) {
            return new UserGroup[size];
        }
    };
}
