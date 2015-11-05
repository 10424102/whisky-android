package org.team10424102.whisky.models;

/**
 * Created by yy on 11/14/15.
 */
public class User {
    private int id;
    private LazyImage avatar;
    private String displayName;
    private String signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LazyImage getAvatar() {
        return avatar;
    }

    public void setAvatar(LazyImage avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
